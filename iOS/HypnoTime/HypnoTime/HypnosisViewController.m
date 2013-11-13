//
//  HypnosisViewController.m
//  HypnoTime
//
//  Created by Tom LePage on 9/26/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "HypnosisViewController.h"
#import "HypnosisView.h"

@implementation HypnosisViewController

- (void)loadView
{
    // Create a view
    CGRect frame = [[UIScreen mainScreen] bounds];
    HypnosisView *v = [[HypnosisView alloc] initWithFrame:frame];
    
    // Set it as -the- view of this view controller
    [self setView:v];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self)
    {
        UITabBarItem *tbi = [self tabBarItem];
        [tbi setTitle:@"Hypnosis"];
        
        UIImage *i = [UIImage imageNamed:@"Hypno.png"];
        [tbi setImage:i];
    }
    
    return self;
}

- (void)viewDidLoad
{
    // Always call super
    [super viewDidLoad];
    
    NSLog(@"HypnosisViewController loaded its view");
}

@end
