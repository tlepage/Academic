//
//  EditorViewController.m
//  ModalEditor
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "EditorViewController.h"

@interface EditorViewController ()

@end

@implementation EditorViewController
@synthesize emailField;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    self.emailField.text = ((ModalEditorViewController *)self.presentingViewController).emailLabel.text;
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)viewDidUnload
{
    [self setEmailField:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
	return YES;
}

- (IBAction)dismissEditor:(id)sender
{
    ((ModalEditorViewController *)self.presentingViewController).emailLabel.text = self.emailField.text;
    [self dismissModalViewControllerAnimated:YES];
}
@end
