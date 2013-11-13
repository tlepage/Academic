//
//  BNRFeedStore.m
//  Nerdfeed
//
//  Created by Tom LePage on 10/8/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "BNRFeedStore.h"
#import "RSSChannel.h"
#import "BNRConnection.h"
#import "RSSItem.h"

@implementation BNRFeedStore

+ (BNRFeedStore *)sharedStore
{
    static BNRFeedStore *feedStore = nil;
    if (!feedStore)
    {
        feedStore = [[BNRFeedStore alloc] init];
    }
    
    return feedStore;
}

- (id)init
{
    self = [super init];
    if (self)
    {
        model = [NSManagedObjectModel mergedModelFromBundles:nil];
        
        NSPersistentStoreCoordinator *psc = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:model];
        
        NSError *error = nil;
        NSString *dbPath = [NSSearchPathForDirectoriesInDomains(NSDocumentationDirectory, NSUserDomainMask, YES) objectAtIndex:0];
        dbPath = [dbPath stringByAppendingPathComponent:@"feed.db"];
        NSURL *dbURL = [NSURL fileURLWithPath:dbPath];
        
        if (![psc addPersistentStoreWithType:NSSQLiteStoreType configuration:nil URL:dbURL options:nil error:&error])
        {
            [NSException raise:@"Open failed" format:@"Reason: %@", [error localizedDescription]];
        }
        
        context = [[NSManagedObjectContext alloc] init];
        [context setPersistentStoreCoordinator:psc];
        
        [context setUndoManager:nil];
    }
    
    return self;
}

- (RSSChannel *)fetchRSSFeedWithCompletion:(void (^)(RSSChannel *, NSError *))block
{
    NSURL *url = [NSURL URLWithString:@"http://forums.bignerdranch.com/"
                  @"smartfeed.php?limit=1_DAY&sort_by=standard"
                  @"&feed_type=RSS2.0&feed_style=COMPACT"];
    
    NSURLRequest *req = [NSURLRequest requestWithURL:url];
    
    // Create an empty channel
    RSSChannel *channel = [[RSSChannel alloc] init];
    
    // Create a connection "actor" object that will transfer data from the server
    BNRConnection *connection = [[BNRConnection alloc] initWithRequest:req];
    
    // When the connection completes, this block from the controller will be called
    //[connection setCompletionBlock:block];
    NSString *cachePath = [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    cachePath = [cachePath stringByAppendingPathComponent:@"nerd.archive"];
    
    // Load the cached channel
    RSSChannel *cachedChannel = [NSKeyedUnarchiver unarchiveObjectWithFile:cachePath];
    
    if (!cachedChannel)
    {
        cachedChannel = [[RSSChannel alloc] init];
    }
    
    RSSChannel *channelCopy = [cachedChannel copy];
    
    [connection setCompletionBlock:^(RSSChannel *obj, NSError *err)
    {
        if (!err)
        {
            //[cachedChannel addItemsFromChannel:obj];
            //[NSKeyedArchiver archiveRootObject:cachedChannel toFile:cachePath];
            [channelCopy addItemsFromChannel:obj];
            [NSKeyedArchiver archiveRootObject:channelCopy toFile:cachePath];
        }
        
        //block(cachedChannel, err);
        block(channelCopy, err);
    }];
    
    
    // Let the empty channel parse the returning data from the web service
    [connection setXmlRootObject:channel];
    
    // Begin the connection
    [connection start];
    
    return cachedChannel;
}

- (void)fetchTopSongs:(int)count withCompletion:(void (^)(RSSChannel *, NSError *))block
{
    // Construct the cache path
    NSString *cachePath = [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    cachePath = [cachePath stringByAppendingPathComponent:@"apple.archive"];
    
    // Make sure that we have at least cached once before by checking to see if this date exists
    NSDate *tscDate = [self topSongsCacheDate];
    if (tscDate)
    {
        // How old is the cache?
        NSTimeInterval cacheAge = [tscDate timeIntervalSinceNow];
        
        if (cacheAge > -300.0)
        {
            // If it is less than 5 minutes old, return cache
            NSLog(@"Reading cache!");
            
            RSSChannel *cachedChannel = [NSKeyedUnarchiver unarchiveObjectWithFile:cachePath];
            
            if (cachedChannel)
            {
                // Execute the controller's completion block to reload its table\
                // To prevent from being called immediately (can cause issues)
                [[NSOperationQueue mainQueue] addOperationWithBlock:^
                {
                    block(cachedChannel, nil);
                }];
                // Don't need to make the request, just exit out
                return;
            }
        }
    }
    
    //NSString *requestString = [NSString stringWithFormat:@"http://itunes.apple.com/us/rss/topsongs/limit=%d/xml", count];
    NSString *requestString = [NSString stringWithFormat:@"http://itunes.apple.com/us/rss/topsongs/limit=%d/json", count];
    
    NSURL *url = [NSURL URLWithString:requestString];
    
    NSURLRequest *req = [[NSURLRequest alloc] initWithURL:url];
    RSSChannel *channel = [[RSSChannel alloc] init];
    
    // Create a connection "actor" object that will transfer data from the server
    BNRConnection *connection = [[BNRConnection alloc] initWithRequest:req];
    
    // When the connection completes, this block from the controller will be called
    //[connection setCompletionBlock:block];
    [connection setCompletionBlock:^(RSSChannel *obj, NSError *err)
    {
        if (!err)
        {
            [self setTopSongsCacheDate:[NSDate date]];
            [NSKeyedArchiver archiveRootObject:obj toFile:cachePath];
        }
        
        block(obj, err);
    }];
    
    // Let the empty channel parse the returning data from the web service
    //[connection setXmlRootObject:channel];
    [connection setJsonRootObject:channel];
    
    // Begin the connection
    [connection start];
}

- (void)setTopSongsCacheDate:(NSDate *)topSongsCacheDate
{
    [[NSUserDefaults standardUserDefaults] setObject:topSongsCacheDate forKey:@"topSongsCacheDate"];
}

- (NSDate *)topSongsCacheDate
{
    return [[NSUserDefaults standardUserDefaults] objectForKey:@"topSongsCacheDate"];
}

- (void)markItemAsRead:(RSSItem *)item
{
    // If the item is already in Core Data, no feed for duplicates
    if ([self hasItemBeenRead:item])
    {
        return;
    }
    
    // Create a new Link object and insert it into the context
    NSManagedObject *obj = [NSEntityDescription insertNewObjectForEntityForName:@"Link" inManagedObjectContext:context];
    
    // Set the Link's urlString from the RSSItem
    [obj setValue:[item link] forKey:@"urlString"];
    
    // Immediately save the changes
    [context save:nil];
}

- (BOOL)hasItemBeenRead:(RSSItem *)item
{
    // Create a request to fetch all Link's with the same urlString as this item's link
    NSFetchRequest *req = [[NSFetchRequest alloc] initWithEntityName:@"Link"];
    
    NSPredicate *pred = [NSPredicate predicateWithFormat:@"urlString like %@", [item link]];
    [req setPredicate:pred];
    
    // If there is at least one Link, then this item has been read before
    NSArray *entries = [context executeFetchRequest:req error:nil];
    if ([entries count] > 0)
    {
        return YES;
    }
    
    // If Core Data has never seen this link, then it hasn't been read
    return NO;
}

@end
