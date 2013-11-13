//
//  MasterViewController.h
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/18/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NameEditorViewController.h"

@class DetailViewController;

@interface MasterViewController : UITableViewController<NameEditorViewControllerDelegate>

@property (strong, nonatomic) DetailViewController *detailViewController;
@property (strong, nonatomic) NSMutableOrderedSet *data;
@property (strong, nonatomic) NSManagedObjectContext *managedObjectContext;

@end
