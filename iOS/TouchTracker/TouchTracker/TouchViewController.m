//
//  TouchViewController.m
//  TouchTracker
//
//  Created by Tom LePage on 10/3/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "TouchViewController.h"
#import "TouchDrawView.h"

@implementation TouchViewController

- (void)loadView
{
    [self setView:[[TouchDrawView alloc] initWithFrame:CGRectZero]];
}

@end
