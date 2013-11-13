//
//  PhotoWheelCellView.m
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/18/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "PhotoWheelCellView.h"
#import <QuartzCore/QuartzCore.h>

@implementation PhotoWheelCellView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setImage:(UIImage *)newImage
{
    // Add the image to the layer's contents.
    CALayer *layer = self.layer;
    id imageRef = (__bridge id)[newImage CGImage];
    [layer setContents:imageRef];
    
    // Add border and shadow
    [layer setBorderColor:[UIColor colorWithWhite:1.0 alpha:1.0].CGColor];
    [layer setBorderWidth:5.0];
    [layer setShadowOffset:CGSizeMake(0, 3)];
    [layer setShadowOpacity:0.7];
    [layer setShouldRasterize:YES];
}

@end
