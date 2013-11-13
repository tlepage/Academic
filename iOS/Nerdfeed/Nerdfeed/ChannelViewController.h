//
//  ChannelViewController.h
//  Nerdfeed
//
//  Created by Tom LePage on 10/5/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ListViewController.h"

@class RSSChannel;

@interface ChannelViewController : UITableViewController <ListViewControllerDelegate, UISplitViewControllerDelegate>
{
    RSSChannel *channel;
}

@end
