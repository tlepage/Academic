//
//  Photo.m
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/19/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "Photo.h"

@implementation Photo

- (UIImage *)image:(UIImage *)image scaleAspectToMaxSize:(CGFloat)newSize
{
    CGSize size = [image size];
    CGFloat ratio;
    
    if (size.width > size.height)
    {
        ratio = newSize / size.width;
    }
    else
    {
        ratio = newSize / size.height;
    }

    CGRect rect = CGRectMake(0.0, 0.0, ratio * size.width, ratio * size.height);
    UIGraphicsBeginImageContext(rect.size);
    [image drawInRect:rect];
    UIImage *scaledImage = UIGraphicsGetImageFromCurrentImageContext();

    return scaledImage;
}

- (UIImage *)image:(UIImage *)image scaleAndCropToMaxSize:(CGSize)newSize
{
    CGFloat largestSize = (newSize.width > newSize.height) ? newSize.width : newSize.height;
    CGSize imageSize = [image size];
    
    // maintain aspect ratio while scaling
    CGFloat ratio;
    if (imageSize.width > imageSize.height)
    {
        ratio = largestSize / imageSize.height;
    }
    else
    {
        ratio = largestSize / imageSize.width;
    }
    
    CGRect rect = CGRectMake(0.0, 0.0, ratio * imageSize.width, ratio * imageSize.height);
    UIGraphicsBeginImageContext(rect.size);
    [image drawInRect:rect];
    UIImage *scaledImage = UIGraphicsGetImageFromCurrentImageContext();
    
    // crop the image
    CGFloat offsetX = 0;
    CGFloat offsetY = 0;
    imageSize = [scaledImage size];
    if (imageSize.width < imageSize.height)
    {
        offsetY = (imageSize.height * 0.5) - (imageSize.width * 0.5);
    }
    else
    {
        offsetX = (imageSize.width * 0.5) - (imageSize.height * 0.5);
    }
    
    CGRect cropRect = CGRectMake(offsetX, offsetY, imageSize.width - (offsetX * 2), imageSize.height - (offsetY * 2));
    CGImageRef croppedImageRef = CGImageCreateWithImageInRect([scaledImage CGImage], cropRect);
    UIImage *newImage = [UIImage imageWithCGImage:croppedImageRef];
    CGImageRelease(croppedImageRef);
    
    return newImage;
}

- (void)saveImage:(UIImage *)newImage
{
    NSData *originalImageData = UIImageJPEGRepresentation(newImage, 0.8);
    [self setOriginalImageData:originalImageData];
    
    // Save thumbnail
    CGSize thumbnailSize = CGSizeMake(75.0, 75.0);
    UIImage *thumbnailImage = [self image:newImage scaleAndCropToMaxSize:thumbnailSize];
    NSData *thumbnailImageData = UIImageJPEGRepresentation(thumbnailImage, 0.8);
    [self setThumbnailImageData:thumbnailImageData];
    
    // Save large
    CGRect screenBounds = [[UIScreen mainScreen] bounds];
    // Calculate the size for retina displays
    CGFloat scale = [[UIScreen mainScreen] scale];
    CGFloat maxScreenSize = MAX(screenBounds.size.width, screenBounds.size.height) * scale;
    
    CGSize imageSize = [newImage size];
    CGFloat maxImageSize = MAX(imageSize.width, imageSize.height) * scale;
    
    CGFloat maxSize = MIN(maxScreenSize, maxImageSize);
    UIImage *largeImage = [self image:newImage scaleAspectToMaxSize:maxSize];
    NSData *largeImageData = UIImageJPEGRepresentation(largeImage, 0.8);
    [self setLargeImageData:largeImageData];
}

- (UIImage *)originalImage
{
    return [UIImage imageWithData:[self originalImageData]];
}

- (UIImage *)largeImage
{
    return [UIImage imageWithData:[self largeImageData]];
}

- (UIImage *)thumbnailImage
{
    return [UIImage imageWithData:[self thumbnailImageData]];
}

@end
