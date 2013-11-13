//
//  ListViewController.m
//  Nerdfeed
//
//  Created by Tom LePage on 10/5/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "ListViewController.h"
#import "RSSChannel.h"
#import "RSSItem.h"
#import "WebViewController.h"
#import "ChannelViewController.h"
#import "BNRFeedStore.h"

@implementation ListViewController
@synthesize webViewController;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    
    if (self)
    {
        UIBarButtonItem *bbi = [[UIBarButtonItem alloc] initWithTitle:@"Info" style:UIBarButtonItemStyleBordered target:self action:@selector(showInfo:)];
        
        [[self navigationItem] setRightBarButtonItem:bbi];
        
        UISegmentedControl *rssTypeControl = [[UISegmentedControl alloc] initWithItems:[NSArray arrayWithObjects:@"BNR", @"Apple", nil]];
        [rssTypeControl setSelectedSegmentIndex:0];
        [rssTypeControl setSegmentedControlStyle:UISegmentedControlStyleBar];
        [rssTypeControl addTarget:self action:@selector(changeType:) forControlEvents:UIControlEventValueChanged];
        [[self navigationItem] setTitleView:rssTypeControl];
        
        [self fetchEntries];
    }
    
    return self;
}

- (void)changeType:(id)sender
{
    rssType = [sender selectedSegmentIndex];
    [self fetchEntries];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad)
    {
        return YES;
    }
    
    return toInterfaceOrientation == UIInterfaceOrientationPortrait;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[channel items] count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"UITableViewCell"];
    
    if (cell == nil)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"UITableViewCell"];
    }
    
    RSSItem *item = [[channel items] objectAtIndex:[indexPath row]];
    [[cell textLabel] setText:[item title]];
    
    if ([[BNRFeedStore sharedStore] hasItemBeenRead:item])
    {
        [cell setAccessoryType:UITableViewCellAccessoryCheckmark];
    }
    else
    {
        [cell setAccessoryType:UITableViewCellAccessoryNone];
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Push the web view controller onto the navigation stack - this implicity
    // creates the web view controller's view the first time through
    if (![self splitViewController])
    {
        [[self navigationController] pushViewController:webViewController animated:YES];
    }
    else
    {
        // We have to create a new navigation controller, as the old one was only retained by the split view controller
        UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:webViewController];
        
        NSArray *vcs = [NSArray arrayWithObjects:[self navigationController], nav, nil];
        
        [[self splitViewController] setViewControllers:vcs];
         
        // Make the detail view controller the delegate of the split view controller
        [[self splitViewController] setDelegate:webViewController];
    }
    
    // Grab the selected item
    RSSItem *entry = [[channel items] objectAtIndex:[indexPath row]];
    
    [[BNRFeedStore sharedStore] markItemAsRead:entry];
    
    // Immediately add a checkmark to this row
    [[[self tableView] cellForRowAtIndexPath:indexPath] setAccessoryType:UITableViewCellAccessoryCheckmark];
    
    /*
    // Construct a URL with the link string of the item
    NSURL *url = [NSURL URLWithString:[entry link]];
    
    // Construct a request object with that URL
    NSURLRequest *req = [NSURLRequest requestWithURL:url];
    
    // Load the request into the web view
    [[webViewController webView] loadRequest:req];
    
    // Set the title of the web view controller's navigation item
    [[webViewController navigationItem] setTitle:[entry title]];
     */
    
    [webViewController listViewController:self handleObject:entry];
    
}

/*
-(void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName
                                    namespaceURI:(NSString *)namespaceURI
                                    qualifiedName:(NSString *)qName
                                    attributes:(NSDictionary *)attributeDict
{
    NSLog(@"%@ found a %@ element", self, elementName);
    
    if ([elementName isEqual:@"channel"])
    {
        // If the parser saw a channel, create a new instance, store in our ivar
        channel = [[RSSChannel alloc] init];
        
        // Give the channel object a pointer back to ourselves for later
        [channel setParentParserDelegate:self];
        
        // Set the parser's delegate to channel object
        [parser setDelegate:channel];
    }
    
}*/

- (void)fetchEntries
{
    NSLog(@"Fetching...");
    
    // Get ahold of the segmented control that is currently in the title view
    UIView *currentTitleView = [[self navigationItem] titleView];
    
    // Create an activity indicator and start it spinning in the nav bar
    UIActivityIndicatorView *aiView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhite];
    [[self navigationItem] setTitleView:aiView];
    [aiView startAnimating];
    
    void (^completionBlock)(RSSChannel *obj, NSError *err) = ^(RSSChannel *obj, NSError *err)
    {
        // When the request completes - success or failure - replace the activity
        // indicator with the segmented control
        [[self navigationItem] setTitleView:currentTitleView];
        
        // When the request is complete, this block will be called
        if (!err)
        {
            channel = obj;
            [[self tableView] reloadData];
        }
        else
        {
            NSString *errorString = [NSString stringWithFormat:@"Fetch failed: %@", [err localizedDescription]];
            // Things went bad...
            UIAlertView *av = [[UIAlertView alloc] initWithTitle:@"Error" message:errorString delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
            [av show];
        }
    };
    
    // Initiate the request...
    if (rssType == ListViewControllerRSSTypeBNR)
    {
        //channel = [[BNRFeedStore sharedStore] fetchRSSFeedWithCompletion:completionBlock];
        channel = [[BNRFeedStore sharedStore] fetchRSSFeedWithCompletion:^(RSSChannel *obj, NSError *err)
        {
            // Replace the activity indicator
            [[self navigationItem] setTitleView:currentTitleView];
            
            if (!err)
            {
                // How many items are there currently?
                int currentItemCount = [[channel items] count];
                
                // Set our channel to the merged one
                channel = obj;
                
                // How many are there now?
                int newItemCount = [[channel items] count];
                
                // For each new item, insert a new row.  The data source will take care of the rest
                int itemDelta = newItemCount - currentItemCount;
                if (itemDelta > 0)
                {
                    NSMutableArray *rows = [NSMutableArray array];
                    for (int i = 0; i < itemDelta; i++)
                    {
                        NSIndexPath *ip = [NSIndexPath indexPathForRow:i inSection:0];
                        [rows addObject:ip];
                    }
                    
                    [[self tableView] insertRowsAtIndexPaths:rows withRowAnimation:UITableViewRowAnimationTop];
                }
            }
        }];
        
        [[self tableView] reloadData];
    }
    else if (rssType == ListViewControllerRSSTypeApple)
    {
        [[BNRFeedStore sharedStore] fetchTopSongs:10 withCompletion:completionBlock];
    }
    
    
    /*
    // Create a new data container for the stuff that comes back from the service
    xmlData = [[NSMutableData alloc] init];
    
    // Construct a URL that will ask the service for what you want-
    // not we can concatenate literal strings together on multiple lines this way
    // This results in a single NSString instance
    NSURL *url = [NSURL URLWithString:
                  @"http://forums.bignerdranch.com/smartfeed.php?"
                  @"limit=1_DAY&sort_by=standard&feed_type=RSS2.0&feed_style=COMPACT"];
    
    // For Apple's Hot News feed, replace the line above with
    // NSURL *url = [NSURL URLWithString@"http://www.apple.com/pr/feeds/pr.rss"
    
    // Put that URL into an NSURLRequest
    NSURLRequest *req = [NSURLRequest requestWithURL:url];
    
    // Create a connection that will exchange this request for data from the URL
    connection = [[NSURLConnection alloc] initWithRequest:req delegate:self startImmediately:YES];
     */
}
/*
// This method will be called several times as the data arrives
- (void)connection:(NSURLConnection *)conn didReceiveData:(NSData *)data
{
    // Add the incoming chunk of data to the container we are keeping
    // The data always comes in the correct order
    [xmlData appendData:data];
}
*/

/*
- (void)connection:(NSURLConnection *)conn didFailWithError:(NSError *)error
{
    // Release the connection object, we're done with it
    connection = nil;
    
    // Release the xmlData object, we're done with it
    xmlData = nil;
    
    // Grab the description of the error passed to us
    NSString *errorString = [NSString stringWithFormat:@"Fetch failed: %@", [error localizedDescription]];
    
    // Create and show an alert view with this error displayed
    UIAlertView *av = [[UIAlertView alloc] initWithTitle:@"Error" message:errorString delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
    [av show];
}
*/

/*
- (void)connectionDidFinishLoading:(NSURLConnection *)conn
{
    // Create the parser object with the data received from the web service
    NSXMLParser *parser = [[NSXMLParser alloc] initWithData:xmlData];
    
    // Give it a delegate
    [parser setDelegate:self];
    
    // Tell it to start parsing - the document will be parsed and the delegate of NSXMLParser
    // will get all of its delegate messages sent to it before this line finishes execution - it is blocking
    [parser parse];
    
    // Get rid of the XML data
    xmlData = nil;
    
    // Get rid of the connection
    connection = nil;
    
    // Reload the table
    [[self tableView] reloadData];
}
*/

- (void)showInfo:(id)sender
{
    // Create the channel view controller
    ChannelViewController *channelViewController = [[ChannelViewController alloc] initWithStyle:UITableViewStyleGrouped];
    
    if ([self splitViewController])
    {
        UINavigationController *nvc = [[UINavigationController alloc] initWithRootViewController:channelViewController];
        
        // Create an array with our nav controller and this new VC's nav controller
        NSArray *vcs = [NSArray arrayWithObjects:[self navigationController], nvc, nil];
        
        // Grab a pointer to the split view controller and reset its view controllers array
        [[self splitViewController] setViewControllers:vcs];
        
        // Make detail view controller the delegate of the split view controller
        [[self splitViewController] setDelegate:channelViewController];
        
        // If a row has been selected, deselect it so that a row is not selected when viewing the info
        NSIndexPath *selectedRow = [[self tableView] indexPathForSelectedRow];
        if (selectedRow)
        {
            [[self tableView] deselectRowAtIndexPath:selectedRow animated:YES];
        }
        else
        {
            [[self navigationController] pushViewController:channelViewController animated:YES];
        }
        
        // Give the VC the channel object through the protocol message
        [channelViewController listViewController:self handleObject:channel];
    }
}

@end
