//
//  GettingAttentionViewController.m
//  GettingAttention
//
//  Created by Tom LePage on 8/28/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "GettingAttentionViewController.h"
#import <AudioToolbox/AudioToolbox.h>

@interface GettingAttentionViewController ()

@end

@implementation GettingAttentionViewController
@synthesize userOutput;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [self setUserOutput:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

- (IBAction)doAlert:(id)sender
{
    UIAlertView *alertDialog;
    alertDialog = [[UIAlertView alloc]
                   initWithTitle:@"Alert Button Selected"
                   message:@"I need your attention NOW!"
                   delegate:nil
                   cancelButtonTitle:@"Ok"
                   otherButtonTitles:nil,
                   nil];
    [alertDialog show];
}

- (IBAction)doMultiButtonAlert:(id)sender
{
    UIAlertView *alertDialog;
    alertDialog = [[UIAlertView alloc]
                   initWithTitle:@"Alert Button Selected"
                   message:@"I need your attention NOW!"
                   delegate:self
                   cancelButtonTitle:@"Ok"
                   otherButtonTitles:@"Maybe Later", @"Never",
                   nil];
    [alertDialog show];
}

- (void)alertView:(UIAlertView *) alertView
        clickedButtonAtIndex:(NSInteger)buttonIndex
{
    NSString *buttonTitle = [alertView buttonTitleAtIndex:buttonIndex];
    if ([buttonTitle isEqualToString:@"Maybe Later"])
    {
        self.userOutput.text = @"Clicked 'Maybe Later'";
    }
    else if ([buttonTitle isEqualToString:@"Never"])
    {
        self.userOutput.text = @"Clicked 'Never'";
    }
    else
    {
        self.userOutput.text = @"Clicked 'Ok'";
    }

    if ([alertView.title isEqualToString:@"Email Address"])
    {
        self.userOutput.text = [[alertView textFieldAtIndex:0] text];
    }
}

- (IBAction)doAlertInput:(id)sender
{
    UIAlertView *alertDialog;
    alertDialog = [[UIAlertView alloc]
                   initWithTitle:@"Email Address"
                   message:@"Please enter your email address:"
                   delegate:self
                   cancelButtonTitle:@"Ok"
                   otherButtonTitles:nil,
                   nil];
    alertDialog.alertViewStyle = UIAlertViewStylePlainTextInput;
    [alertDialog show];
}

- (IBAction)doActionSheet:(id)sender
{
    UIActionSheet *actionSheet;
    actionSheet = [[UIActionSheet alloc] initWithTitle:@"Available Actions"
                    delegate:self
                    cancelButtonTitle:@"Cancel"
                    destructiveButtonTitle:@"Destroy"
                    otherButtonTitles:@"Negotiate", @"Compromise",
                   nil];
    actionSheet.actionSheetStyle = UIActionSheetStyleBlackTranslucent;
    [actionSheet showFromRect:[(UIButton *)sender frame] inView:self.view animated:YES];
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    NSString *buttonTitle = [actionSheet buttonTitleAtIndex:buttonIndex];
    if ([buttonTitle isEqualToString:@"Destroy"])
    {
        self.userOutput.text = @"Clicked 'Destroy'";
    }
    else if ([buttonTitle isEqualToString:@"Negotiate"])
    {
        self.userOutput.text = @"Clicked 'Negotiate'";
    }
    else if ([buttonTitle isEqualToString:@"Compromise"])
    {
        self.userOutput.text = @"Clicked 'Compromise'";
    }
    else
    {
        self.userOutput.text = @"Clicked 'Cancel'";
    }
}

- (IBAction)doSound:(id)sender
{
    SystemSoundID soundID;
    NSString *soundFile = [[NSBundle mainBundle]
                           pathForResource:@"soundeffect" ofType:@"wav"];
    
    AudioServicesCreateSystemSoundID((__bridge CFURLRef)
                                     [NSURL fileURLWithPath:soundFile],
                                     &soundID);
    AudioServicesPlaySystemSound(soundID);
}

- (IBAction)doAlertSound:(id)sender
{
    SystemSoundID soundID;
    NSString *soundFile = [[NSBundle mainBundle]
                           pathForResource:@"soundeffect" ofType:@"wav"];
    
    AudioServicesCreateSystemSoundID((__bridge CFURLRef)
                                     [NSURL fileURLWithPath:soundFile],
                                     &soundID);
    AudioServicesPlayAlertSound(soundID);
}

- (IBAction)doVibration:(id)sender
{
    AudioServicesPlaySystemSound(kSystemSoundID_Vibrate);
}

@end
