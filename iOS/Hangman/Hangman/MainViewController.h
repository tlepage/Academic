//
//  MainViewController.h
//  Hangman
//
//  Created by Tom LePage on 10/16/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "FlipsideViewController.h"

@interface MainViewController : UIViewController <FlipsideViewControllerDelegate, UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UITextField *wordField;
@property (weak, nonatomic) IBOutlet UIProgressView *statusView;
@property (weak, nonatomic) IBOutlet UILabel *lettersRemainingLabel;
@property (weak, nonatomic) IBOutlet UILabel *computerCommentField;
@property (weak, nonatomic) IBOutlet UILabel *wordLabel;

- (IBAction)showInfo:(id)sender;
- (IBAction)newGame:(id)sender;

@end
