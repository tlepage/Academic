//
//  BNRFeedStore.h
//  Nerdfeed
//
//  Created by Tom LePage on 10/8/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class RSSChannel;
@class RSSItem;

@interface BNRFeedStore : NSObject
{
    NSManagedObjectContext *context;
    NSManagedObjectModel *model;
}

+ (BNRFeedStore *)sharedStore;

- (void)fetchTopSongs:(int)count withCompletion:(void (^)(RSSChannel *obj, NSError *err))block;

- (RSSChannel *)fetchRSSFeedWithCompletion:(void (^)(RSSChannel *obj, NSError *err))block;

- (void)markItemAsRead:(RSSItem *)item;
- (BOOL)hasItemBeenRead:(RSSItem *)item;

@property (nonatomic, strong) NSDate *topSongsCacheDate;
@end
