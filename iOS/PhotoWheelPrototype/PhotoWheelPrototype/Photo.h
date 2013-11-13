//
//  Photo.h
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/19/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "_Photo.h"

@interface Photo : _Photo

- (void)saveImage:(UIImage *)newImage;
- (UIImage *)originalImage;
- (UIImage *)largeImage;
- (UIImage *)thumbnailImage;

@end
