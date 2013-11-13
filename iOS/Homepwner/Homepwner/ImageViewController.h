//
//  ImageViewController.h
//  Homepwner
//
//  Created by Tom LePage on 10/2/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ImageViewController : UIViewController
{
    __weak IBOutlet UIImageView *imageView;
    __weak IBOutlet UIScrollView *scrollView;
}

@property (nonatomic, strong) UIImage *image;

@end
