//
//  FlashlightViewController.h
//  Flashlight
//
//  Created by Tom LePage on 8/22/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface FlashlightViewController : UIViewController

@property BOOL isOn;

- (IBAction)turnOn:(id)sender;
- (IBAction)turnOff:(id)sender;

@property (weak, nonatomic) IBOutlet UILabel *indicator;

@property (weak, nonatomic) IBOutlet UIButton *onButton;
@property (weak, nonatomic) IBOutlet UIButton *offButton;

@end
