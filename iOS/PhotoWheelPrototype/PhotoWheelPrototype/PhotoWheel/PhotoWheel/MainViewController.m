//
//  MainViewController.m
//  PhotoWheel
//
//  Created by Tom LePage on 9/20/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "MainViewController.h"
#import "PhotoAlbumViewController.h"
#import "PhotoAlbumsViewController.h"

@interface MainViewController ()

@end

@implementation MainViewController

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
        
    UIStoryboard *storyboard = [self storyboard];
    
    PhotoAlbumsViewController *photoAlbumsScene;
    photoAlbumsScene = [storyboard instantiateViewControllerWithIdentifier:@"PhotoAlbumsScene"];
    [self addChildViewController:photoAlbumsScene];
    [photoAlbumsScene didMoveToParentViewController:self];
    
    PhotoAlbumViewController *photoAlbumScene;
    photoAlbumScene = [storyboard instantiateViewControllerWithIdentifier:@"PhotoAlbumScene"];
    [self addChildViewController:photoAlbumScene];
    [photoAlbumScene didMoveToParentViewController:self];
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

@end
