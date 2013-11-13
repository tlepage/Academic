//
//  TimeViewController.m
//  HypnoTime
//
//  Created by Tom LePage on 9/26/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "TimeViewController.h"
#import <QuartzCore/QuartzCore.h>

@implementation TimeViewController

- (IBAction)showCurrentTime:(id)sender
{
    NSDate *now = [NSDate date];
    
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setTimeStyle:NSDateFormatterMediumStyle];
    
    [timeLabel setText:[formatter stringFromDate:now]];
    
    //[self spinTimeLabel];
    [self bounceTimeLabel];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    // Get a pointer to the application bundle object
    NSBundle *appBundle = [NSBundle mainBundle];
    
    // This is explicit; not necessary if you name the XIB and class file the same
    self = [super initWithNibName:@"TimeViewController" bundle:appBundle];
    if (self)
    {
        UITabBarItem *tbi = [self tabBarItem];
        [tbi setTitle:@"Time"];
        
        UIImage *i = [UIImage imageNamed:@"Time.png"];
        [tbi setImage:i];
    }
    
    return self;
}

- (void)animationDidStop:(CAAnimation *)anim finished:(BOOL)flag
{
    NSLog(@"%@ finished: %d", anim, flag);
}

- (void)spinTimeLabel
{
    // Create the basic animation
    CABasicAnimation *spin = [CABasicAnimation animationWithKeyPath:@"transform.rotation"];
    
    [spin setDelegate:self];
    
    // fromValue is implied
    [spin setToValue:[NSNumber numberWithFloat:M_PI * 2.0]];
    [spin setDuration:1.0];
    
    // Set the timing function
    CAMediaTimingFunction *tf = [CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut];
    [spin setTimingFunction:tf];
    
    // Kick off the animation by adding it to the layer
    [[timeLabel layer] addAnimation:spin forKey:@"spinAnimation"];
}

- (void)bounceTimeLabel
{
    // Create a keyframe animation
    CAKeyframeAnimation *bounce = [CAKeyframeAnimation animationWithKeyPath:@"transform"];
    
    // Create the values it will pass through
    CATransform3D forward = CATransform3DMakeScale(1.3, 1.3, 1);
    CATransform3D back = CATransform3DMakeScale(0.7, 0.7, 1);
    CATransform3D forward2 = CATransform3DMakeScale(1.2, 1.2, 1);
    CATransform3D back2 = CATransform3DMakeScale(0.9, 0.9, 1);
    [bounce setValues:[NSArray arrayWithObjects:
                         [NSValue valueWithCATransform3D:CATransform3DIdentity],
                         [NSValue valueWithCATransform3D:forward],
                         [NSValue valueWithCATransform3D:back],
                         [NSValue valueWithCATransform3D:forward2],
                         [NSValue valueWithCATransform3D:back2],
                       [NSValue valueWithCATransform3D:CATransform3DIdentity], nil]];
    
    // Set the duration
    [bounce setDuration:0.6];
    
    // Animate the layer
    [[timeLabel layer] addAnimation:bounce forKey:@"bounceAnimation"];
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self showCurrentTime:nil];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
}

@end
