//
//  CustomPushSegue.m
//  PhotoWheel
//
//  Created by Tom LePage on 9/19/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "CustomPushSegue.h"
#import "UIView+PWCategory.h"

@implementation CustomPushSegue

- (void)perform
{
    UIView *sourceView = [[[self sourceViewController] parentViewController] view];
    UIView *destinationView = [[self destinationViewController] view];
    
    UIImageView *sourceImageView;
    sourceImageView = [[UIImageView alloc] initWithImage:[sourceView pw_imageSnapshot]];
    
    UIImageView *destinationImageView;
    destinationImageView = [[UIImageView alloc] initWithImage:[destinationView pw_imageSnapshot]];
    
    CGRect originalFrame = [destinationImageView frame];
    [destinationImageView setFrame:CGRectMake(originalFrame.size.width * 0.5,
                                              originalFrame.size.height * 0.5,
                                              0,
                                              0)];
    [destinationImageView setAlpha:0.3];
    
    UINavigationController *navController;
    navController = [[self sourceViewController] navigationController];
    [navController pushViewController:[self destinationViewController] animated:NO];
    
    UINavigationBar *navBar = [navController navigationBar];
    [navController setNavigationBarHidden:NO];
    [navBar setFrame:CGRectOffset(navBar.frame, 0, -navBar.frame.size.height)];
    
    [destinationView addSubview:sourceImageView];
    [destinationView addSubview:destinationImageView];
    
    void (^animations)(void) = ^
    {
        [destinationImageView setFrame:originalFrame];
        [destinationImageView setAlpha:1.0];
        
        [navBar setFrame:CGRectOffset(navBar.frame, 0, navBar.frame.size.height)];
    };
    
    void (^completion)(BOOL) = ^(BOOL finished)
    {
        if (finished)
        {
            [sourceImageView removeFromSuperview];
            [destinationImageView removeFromSuperview];
        }
    };
    
    [UIView animateWithDuration:0.6 animations:animations completion:completion];
}

@end
