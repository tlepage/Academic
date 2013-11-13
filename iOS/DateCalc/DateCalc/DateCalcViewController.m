//
//  DateCalcViewController.m
//  DateCalc
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "DateCalcViewController.h"

@interface DateCalcViewController ()

@end

@implementation DateCalcViewController
@synthesize outputLabel;
@synthesize dateChooserVisible;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [self setOutputLabel:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    ((DateChooserViewController *)segue.destinationViewController).delegate = self;
}

- (IBAction)showDateChooser:(id)sender
{
    if (!dateChooserVisible)
    {
        [self performSegueWithIdentifier:@"toDateChooser" sender:sender];
        dateChooserVisible = YES;
    }
}

- (void)calculateDateDifference:(NSDate *)chosenDate
{
    NSDate *todaysDate;
    NSString *differenceOutput;
    NSString *todaysDateString;
    NSString *chosenDateString;
    NSDateFormatter *dateFormat;
    
    NSTimeInterval difference;
    
    todaysDate = [NSDate date];
    difference = [todaysDate timeIntervalSinceDate:chosenDate] / 86400;
    
    dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"MMMM d, yyyy hh:mm:ssa"];
    todaysDateString = [dateFormat stringFromDate:todaysDate];
    chosenDateString = [dateFormat stringFromDate:chosenDate];
    
    differenceOutput = [[NSString alloc] initWithFormat:@"Difference between chosen date (%@) and today (%@) in days: %1.2f",
                        chosenDateString, todaysDateString, fabs(difference)];
    self.outputLabel.text = differenceOutput;
    
}

@end
