//
//  CustomPickerViewController.m
//  CustomPicker
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "CustomPickerViewController.h"

@interface CustomPickerViewController ()

@end

@implementation CustomPickerViewController
@synthesize outputLabel;
@synthesize animalChooserVisible;

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
    ((AnimalChooserViewController *)segue.destinationViewController).delegate = self;
}

- (IBAction)showAnimalChooser:(id)sender
{
    if (!animalChooserVisible)
    {
        [self performSegueWithIdentifier:@"toAnimalChooser" sender:sender];
        animalChooserVisible = YES;
    }
}

- (void)displayAnimal:(NSString *)chosenAnimal withSound:(NSString *)chosenSound fromComponent:(NSString *)chosenComponent
{
    NSString *animalSoundString = [[NSString alloc]
                                   initWithFormat:@"You changed %@ (%@ and the sound %@)",
                                   chosenComponent, chosenAnimal, chosenSound];
    self.outputLabel.text = animalSoundString;
}

@end
