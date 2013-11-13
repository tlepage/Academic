//
//  DateChooserViewController.h
//  DateCalc
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DateCalcViewController.h"

@interface DateChooserViewController : UIViewController

@property (strong, nonatomic) id delegate;

- (IBAction)setDateTime:(id)sender;

@end
