//
//  PhotoAlbumViewController.m
//  PhotoWheel
//
//  Created by Tom LePage on 9/20/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "PhotoAlbumViewController.h"

@interface PhotoAlbumViewController ()

@end

@implementation PhotoAlbumViewController

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
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)didMoveToParentViewController:(UIViewController *)parent
{
    // position the view within the new parent
    [[parent view] addSubview:[self view]];
    CGRect newFrame = CGRectMake(26, 18, 716, 717);
    [[self view] setFrame:newFrame];
    
    [[self view] setBackgroundColor:[UIColor clearColor]];
}

@end
