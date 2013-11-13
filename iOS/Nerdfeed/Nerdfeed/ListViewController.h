//
//  ListViewController.h
//  Nerdfeed
//
//  Created by Tom LePage on 10/5/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>

@class RSSChannel;
@class WebViewController;

typedef enum
{
    ListViewControllerRSSTypeBNR,
    ListViewControllerRSSTypeApple
}ListViewControllerRSSType;

@interface ListViewController : UITableViewController //<NSXMLParserDelegate>
{
    //NSURLConnection *connection;
    //NSMutableData *xmlData;
    
    RSSChannel *channel;
    ListViewControllerRSSType rssType;
}

@property (nonatomic, strong) WebViewController *webViewController;

- (void)fetchEntries;

@end

// A new protocol named ListViewControllerDelegate
@protocol ListViewControllerDelegate

- (void)listViewController:(ListViewController *)lvc handleObject:(id)object;

@end
