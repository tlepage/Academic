//
//  AskerViewController.m
//  Kitchen Sink
//
//  Created by Tom LePage on 7/26/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "AskerViewController.h"

@interface AskerViewController () <UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UILabel *questionLabel;
@property (weak, nonatomic) IBOutlet UITextField *answerTextField;

@end

@implementation AskerViewController
@synthesize questionLabel = _questionLabel;
@synthesize answerTextField = _answerTextField;
@synthesize question = _question;
@synthesize answer = _answer;

- (void)setQuestion:(NSString *)question
{
    _question = question;
    self.questionLabel.text = question;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.questionLabel.text = self.question;
    self.answerTextField.placeholder = self.answer;
    self.answerTextField.delegate = self;
    
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self.answerTextField becomeFirstResponder];
}

- (void) textFieldDidEndEditing:(UITextField *)textField
{
    self.answer = textField.text;
    
    if (![textField.text length])
    {
        [[self presentingViewController] dismissModalViewControllerAnimated:YES];
    }
    else
    {
        // communicate something back
        [self.delegate askerViewController:self didAskQuestion:self.question andGotAnswer:self.answer];
    }
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    if ([textField.text length])
    {
        [textField resignFirstResponder];
        return YES;
    }
    else
    {
        return NO;
    }
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

- (void)viewDidUnload {
    [self setQuestionLabel:nil];
    [self setAnswerTextField:nil];
    [super viewDidUnload];
}
@end
