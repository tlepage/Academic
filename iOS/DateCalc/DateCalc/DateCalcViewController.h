//
//  DateCalcViewController.h
//  DateCalc
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DateChooserViewController.h"

@interface DateCalcViewController : UIViewController

@property (weak, nonatomic) IBOutlet UILabel *outputLabel;
@property (nonatomic) Boolean dateChooserVisible;

- (IBAction)showDateChooser:(id)sender;
- (void)calculateDateDifference:(NSDate *)chosenDate;

@end
