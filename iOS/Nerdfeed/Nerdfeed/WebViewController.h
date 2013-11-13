//
//  WebViewController.h
//  Nerdfeed
//
//  Created by Tom LePage on 10/5/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ListViewController.h"

@interface WebViewController : UIViewController <ListViewControllerDelegate, UISplitViewControllerDelegate>

@property (nonatomic, readonly) UIWebView *webView;

@end
