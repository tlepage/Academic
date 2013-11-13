//
//  CustomPickerViewController.h
//  CustomPicker
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AnimalChooserViewController.h"

@interface CustomPickerViewController : UIViewController

@property (weak, nonatomic) IBOutlet UILabel *outputLabel;
@property (nonatomic) Boolean animalChooserVisible;

- (IBAction)showAnimalChooser:(id)sender;
- (void)displayAnimal:(NSString *)chosenAnimal
            withSound:(NSString *)chosenSound
        fromComponent:(NSString *)chosenComponent;
@end
