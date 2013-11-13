//
//  ItemsViewController.h
//  Homepwner
//
//  Created by Tom LePage on 9/27/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DetailViewController.h"
#import "HomepwnerItemCell.h"

@interface ItemsViewController : UITableViewController <UIPopoverControllerDelegate>
{
    UIPopoverController *imagePopover;
    //IBOutlet UIView *headerView;
}

//- (UIView *)headerView;
- (IBAction)addNewItem:(id)sender;
//- (IBAction)toggleEditingMode:(id)sender;

@end
