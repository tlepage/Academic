//
//  PopoverEditorViewController.m
//  PopoverEditor
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "PopoverEditorViewController.h"

@interface PopoverEditorViewController ()

@end

@implementation PopoverEditorViewController
@synthesize emailLabel;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [self setEmailLabel:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    UIStoryboardPopoverSegue *popoverSegue;
    popoverSegue = (UIStoryboardPopoverSegue *)segue;
    
    UIPopoverController *popoverController;
    popoverController = popoverSegue.popoverController;
    popoverController.delegate = self;
    
    EditorViewController *editorVC;
    editorVC = (EditorViewController *)popoverController.contentViewController;
    editorVC.emailField.text = self.emailLabel.text;
}

- (void)popoverControllerDidDismissPopover:(UIPopoverController *)popoverController
{
    NSString *newEmail = ((EditorViewController *) popoverController.contentViewController).emailField.text;
    self.emailLabel.text = newEmail;
}

@end
