//
//  UIView+PWCategory.m
//  PhotoWheel
//
//  Created by Tom LePage on 9/19/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "UIView+PWCategory.h"
#import <QuartzCore/QuartzCore.h>

@implementation UIView (PWCategory)

- (UIImage *)pw_imageSnapshot
{
    UIGraphicsBeginImageContext([self bounds].size);
    [[self layer] renderInContext:UIGraphicsGetCurrentContext()];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return image;
}

@end
