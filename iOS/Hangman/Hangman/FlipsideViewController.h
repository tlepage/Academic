//
//  FlipsideViewController.h
//  Hangman
//
//  Created by Tom LePage on 10/16/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@class FlipsideViewController;

@protocol FlipsideViewControllerDelegate
- (void)flipsideViewControllerDidFinish:(FlipsideViewController *)controller;
@end

@interface FlipsideViewController : UIViewController <UITextFieldDelegate>

@property (weak, nonatomic) id <FlipsideViewControllerDelegate> delegate;
@property (weak, nonatomic) IBOutlet UITextField *nameField;
@property (weak, nonatomic) IBOutlet UILabel *welcomeLabel;
@property (weak, nonatomic) IBOutlet UISlider *lettersSlider;
@property (weak, nonatomic) IBOutlet UISwitch *typeSwitch;
@property (weak, nonatomic) IBOutlet UILabel *lettersLabel;
@property (weak, nonatomic) IBOutlet UILabel *lettersQuestionLabel;
@property (weak, nonatomic) IBOutlet UISlider *guessSlider;
@property (weak, nonatomic) IBOutlet UILabel *guessLabel;

- (IBAction)done:(id)sender;
- (IBAction)sliderChanged:(id)sender;
- (IBAction)typeSwitched:(id)sender;
- (IBAction)guessSwitched:(id)sender;

@end
