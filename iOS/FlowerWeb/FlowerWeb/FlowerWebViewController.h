//
//  FlowerWebViewController.h
//  FlowerWeb
//
//  Created by Tom LePage on 8/28/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface FlowerWebViewController : UIViewController
@property (weak, nonatomic) IBOutlet UISegmentedControl *colorChoice;
@property (weak, nonatomic) IBOutlet UIWebView *flowerView;
@property (weak, nonatomic) IBOutlet UIWebView *flowerDetailView;

- (IBAction)getFlower:(id)sender;
- (IBAction)toggleFlowerDetail:(id)sender;

@end
