//
//  FlipsideViewController.m
//  Hangman
//
//  Created by Tom LePage on 10/16/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "FlipsideViewController.h"

@interface FlipsideViewController ()

@end

@implementation FlipsideViewController

@synthesize nameField = _nameField;
@synthesize lettersSlider = _lettersSlider;
@synthesize typeSwitch = _typeSwitch;
@synthesize welcomeLabel = _welcomeLabel;
@synthesize lettersQuestionLabel = _lettersQuestionLabel;
@synthesize guessSlider = _guessSlider;
@synthesize guessLabel = _guessLabel;

- (void)viewDidLoad
{
    [super viewDidLoad];
	
    self.welcomeLabel.hidden = YES;
    
    // Set view controller as delegate for text field messages
    self.nameField.delegate = self;
    
    // Set default values
    NSMutableDictionary *defaultValues = [[NSMutableDictionary alloc] init];
    [defaultValues setObject:@"" forKey:@"name"];
    [defaultValues setObject:@"1" forKey:@"letters"];
    [defaultValues setObject:@"YES" forKey:@"type"];
    [defaultValues setObject:@"5" forKey:@"guesses"];
    
    // Register defaults
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults registerDefaults:defaultValues];
    
    // Load saved data
    self.nameField.text = [defaults stringForKey:@"name"];
    self.typeSwitch.on = [defaults boolForKey:@"type"];
    
    // Get guesses set up
    int guessSliderValue = [defaults integerForKey:@"guesses"];
    if (guessSliderValue == 1)
    {
        guessSliderValue = self.guessSlider.minimumValue;
    }
    self.guessSlider.value = guessSliderValue;
    self.guessLabel.text = [NSString stringWithFormat:@"%d", guessSliderValue];
    
    // Check if game type switch is set to evil; if not, don't display the number of letters
    if (self.typeSwitch.on == YES)
    {        
        int sliderValue = [defaults integerForKey:@"letters"];
    
        if (sliderValue == 1)
        {
            sliderValue = self.lettersSlider.minimumValue;
        }
    
        self.lettersSlider.value = sliderValue;
    
        self.lettersLabel.text = [NSString stringWithFormat:@"%d", sliderValue];
    }
    else
    {
        // Hide all items related to picking number of letters
        self.lettersLabel.hidden = YES;
        self.lettersSlider.hidden = YES;
        self.lettersQuestionLabel.hidden = YES;
    }
    
    // If there is no name, don't display the welcome message
    if (self.nameField.text != nil && self.nameField.text != @"Welcome, !")
    {
        self.welcomeLabel.text = [NSString stringWithFormat:@"Welcome, %@!", self.nameField.text];
        self.welcomeLabel.hidden = NO;
    }
    
    // Set auto-adjust for name field
    self.nameField.adjustsFontSizeToFitWidth = YES;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - View Controller Actions

- (IBAction)done:(id)sender
{
    // Set the defaults
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    
    [defaults setObject:self.nameField.text forKey:@"name"];
    [defaults setInteger:self.lettersSlider.value forKey:@"letters"];
    [defaults setBool:self.typeSwitch.isOn forKey:@"type"];
    [defaults setInteger:self.guessSlider.value forKey:@"guesses"];
    
    [self.delegate flipsideViewControllerDidFinish:self];
}


#pragma mark - UITextFieldDelegate Actions

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return YES;
}

#pragma mark - UISlider Actions

- (IBAction)sliderChanged:(id)sender
{
    int sliderValue = 1;
    sliderValue = (int)round(self.lettersSlider.value);
    [self.lettersSlider setValue:sliderValue animated:YES];
    self.lettersLabel.text = [NSString stringWithFormat:@"%d", sliderValue];
}

- (IBAction)guessSwitched:(id)sender
{
    int sliderValue = 1;
    sliderValue = (int)round(self.guessSlider.value);
    [self.guessSlider setValue:sliderValue animated:YES];
    self.guessLabel.text = [NSString stringWithFormat:@"%d", sliderValue];
}

#pragma mark - UISwitch Actions

- (IBAction)typeSwitched:(id)sender
{
    if(self.typeSwitch.on == YES)
    {
        self.lettersLabel.hidden = NO;
        self.lettersSlider.hidden = NO;
        self.lettersQuestionLabel.hidden = NO;
    }
    else
    {
        self.lettersLabel.hidden = YES;
        self.lettersSlider.hidden = YES;
        self.lettersQuestionLabel.hidden = YES;
    }
}

@end
