//
//  AnimalChooserViewController.m
//  CustomPicker
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "AnimalChooserViewController.h"

#define kComponentCount 2
#define kAnimalComponent 0
#define kSoundComponent 1

@interface AnimalChooserViewController ()

@end

@implementation AnimalChooserViewController
@synthesize delegate;
@synthesize animalImages;
@synthesize animalNames;
@synthesize animalSounds;

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
    self.animalNames = [[NSArray alloc] initWithObjects:
                        @"Mouse", @"Goose", @"Cat", @"Dog", @"Snake", @"Bear", @"Pig", nil];
    self.animalSounds = [[NSArray alloc] initWithObjects:
                         @"Oink", @"Rawr", @"Ssss", @"Woof", @"Meow", @"Honk", @"Squeak", nil];
    self.animalImages = [[NSArray alloc] initWithObjects:
                         [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"mouse.png"]],
                         [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"goose.png"]],
                         [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"cat.png"]],
                         [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"dog.png"]],
                         [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"snake.png"]],
                         [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"bear.png"]],
                         [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"pig.png"]],
                         nil];
    
    
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)viewDidUnload
{
    [self setDelegate:nil];
    [self setAnimalImages:nil];
    [self setAnimalNames:nil];
    [self setAnimalSounds:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (void)viewWillDisappear:(BOOL)animated
{
    ((CustomPickerViewController *)self.delegate).animalChooserVisible = NO;
}

- (void)viewDidAppear:(BOOL)animated
{
    CustomPickerViewController *initialView;
    initialView = (CustomPickerViewController *)self.delegate;
    [initialView displayAnimal:[self.animalNames objectAtIndex:0]
                     withSound:[self.animalSounds objectAtIndex:0]
                 fromComponent:@"nothing yet..."];
}
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
	return YES;
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return kComponentCount;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    if (component == kAnimalComponent)
    {
        return [self.animalNames count];
    }
    else
    {
        return [self.animalSounds count];
    }
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view
{
    if (component == kAnimalComponent)
    {
        return [self.animalImages objectAtIndex:row];
    }
    else
    {
        // We can't just display a string here; we need to make a label object to hold the string
        UILabel *soundLabel;
        soundLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 100, 32)];
        soundLabel.backgroundColor = [UIColor clearColor];
        soundLabel.text = [self.animalSounds objectAtIndex:row];
        
        return soundLabel;
    }
}

- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component
{
    return 55.0;
}

- (CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    if (component == kAnimalComponent)
    {
        return 75.0;
    }
    else
    {
        return 150.0;
    }
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    CustomPickerViewController *initialView = (CustomPickerViewController *)self.delegate;
    
    if (component == kAnimalComponent)
    {
        int chosenSound = [pickerView selectedRowInComponent:kSoundComponent];
        [initialView displayAnimal:[self.animalNames objectAtIndex:row]
                         withSound:[self.animalSounds objectAtIndex:chosenSound]
                     fromComponent:@"the Animal"];
    }
    else
    {
        int chosenAnimal = [pickerView selectedRowInComponent:kAnimalComponent];
        [initialView displayAnimal:[self.animalNames objectAtIndex:chosenAnimal]
                         withSound:[self.animalSounds objectAtIndex:row]
                     fromComponent:@"the Sound"];
    }
}

@end
