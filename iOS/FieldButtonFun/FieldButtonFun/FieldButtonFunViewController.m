//
//  FieldButtonFunViewController.m
//  FieldButtonFun
//
//  Created by Tom LePage on 8/28/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "FieldButtonFunViewController.h"

@interface FieldButtonFunViewController ()

@end

@implementation FieldButtonFunViewController

@synthesize thePlace;
@synthesize theVerb;
@synthesize theNumber;
@synthesize theTemplate;
@synthesize theStory;
@synthesize theButton;

- (void)viewDidLoad
{
    UIImage *normalImage = [[UIImage imageNamed:@"whiteButton.png"]
                            stretchableImageWithLeftCapWidth:12.0
                            topCapHeight:0.0];
    UIImage *pressedImage = [[UIImage imageNamed:@"blueButton.png"]
                             stretchableImageWithLeftCapWidth:12.0
                             topCapHeight:0.0];
    
    [self.theButton setBackgroundImage:normalImage forState:UIControlStateNormal];
    [self.theButton setBackgroundImage:pressedImage forState:UIControlStateHighlighted];
    
    [super viewDidLoad];
}

- (void)viewDidUnload
{
    [self setThePlace:nil];
    [self setTheVerb:nil];
    [self setTheNumber:nil];
    [self setTheTemplate:nil];
    [self setTheStory:nil];
    [self setTheButton:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

- (IBAction)createStory:(id)sender
{
    self.theStory.text = [self.theTemplate.text
                          stringByReplacingOccurrencesOfString:@"<place>"
                          withString:self.thePlace.text];
    self.theStory.text = [self.theStory.text
                          stringByReplacingOccurrencesOfString:@"<verb>"
                          withString:self.theVerb.text];
    self.theStory.text = [self.theStory.text
                          stringByReplacingOccurrencesOfString:@"<number>"
                          withString:self.theNumber.text];
}

- (IBAction)hideKeyboard:(id)sender
{
    [self.thePlace resignFirstResponder];
    [self.theVerb resignFirstResponder];
    [self.theNumber resignFirstResponder];
    [self.theTemplate resignFirstResponder];
}
@end
