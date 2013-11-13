//
//  ImageHopViewController.h
//  ImageHop
//
//  Created by Tom LePage on 8/28/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ImageHopViewController : UIViewController

@property (weak, nonatomic) IBOutlet UIImageView *bunnyView1;
@property (weak, nonatomic) IBOutlet UIImageView *bunnyView2;
@property (weak, nonatomic) IBOutlet UIImageView *bunnyView3;
@property (weak, nonatomic) IBOutlet UIImageView *bunnyView4;
@property (weak, nonatomic) IBOutlet UIImageView *bunnyView5;
@property (weak, nonatomic) IBOutlet UISlider *speedSlider;
@property (weak, nonatomic) IBOutlet UIStepper *speedStepper;
@property (weak, nonatomic) IBOutlet UILabel *hopsPerSecond;
@property (weak, nonatomic) IBOutlet UIButton *toggleButton;

- (IBAction)toggleAnimation:(id)sender;
- (IBAction)setSpeed:(id)sender;
- (IBAction)setIncrement:(id)sender;

@end
