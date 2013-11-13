//
//  NameEditorViewController.m
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/18/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "NameEditorViewController.h"

@interface NameEditorViewController ()

@end

@implementation NameEditorViewController

@synthesize nameTextField = _nameTextField;
@synthesize delegate = _delegate;
@synthesize indexPath = _indexPath;
@synthesize defaultNameText = _defaultNameText;

- (id)initWithDefaultNib
{
    self = [super initWithNibName:@"NameEditorViewController" bundle:nil];
    
    if (self)
    {
        // custom init
    }
    
    return self;
}

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
    [super viewDidLoad];
    if ([self isEditing])
    {
        [self.nameTextField setText:self.defaultNameText];
    }
}

- (void)viewDidUnload
{
    [self setNameTextField:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

#pragma - Actions Methods

- (IBAction)cancel:(id)sender
{
    id<NameEditorViewControllerDelegate> delegate = [self delegate];
    if (delegate)
    {
        [delegate nameEditorViewControllerDidCancel:self];
    }
    [self dismissModalViewControllerAnimated:YES];
}

- (IBAction)done:(id)sender
{
    id<NameEditorViewControllerDelegate> delegate = [self delegate];
    if (delegate)
    {
        [delegate nameEditorViewControllerDidFinish:self];
    }
    [self dismissModalViewControllerAnimated:YES];
}

@end
