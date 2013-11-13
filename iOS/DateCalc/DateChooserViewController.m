//
//  DateChooserViewController.m
//  DateCalc
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "DateChooserViewController.h"

@interface DateChooserViewController ()

@end

@implementation DateChooserViewController

@synthesize delegate;

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
	// Do any additional setup after loading the view.
}

- (void)viewDidUnload
{
    [self setDelegate:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
	return YES;
}

- (void)viewDidAppear:(BOOL)animated
{
    [(DateCalcViewController *)self.delegate calculateDateDifference:[NSDate date]];
}

- (void)viewWillDisappear:(BOOL)animated
{
    ((DateCalcViewController *)self.delegate).dateChooserVisible = NO;
}

- (IBAction)setDateTime:(id)sender
{
    [(DateCalcViewController *)self.delegate calculateDateDifference:((UIDatePicker *)sender).date];
}

@end
