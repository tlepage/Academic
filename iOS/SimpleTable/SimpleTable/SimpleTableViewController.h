//
//  SimpleTableViewController.h
//  SimpleTable
//
//  Created by Tom LePage on 8/23/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SimpleTableViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>
// need to add those protocols to conform to UITableView's needs
@property (nonatomic, strong) NSArray *tableData;
@property (nonatomic, strong) NSArray *thumbnails;
@property (nonatomic, strong) NSArray *prepTimes;

@end
