//
//  DetailViewController.h
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/18/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WheelView.h"

@class PhotoAlbum;

@interface DetailViewController : UIViewController <UISplitViewControllerDelegate, WheelViewDataSource, UIActionSheetDelegate,
                                                    UINavigationControllerDelegate, UIImagePickerControllerDelegate>

@property (strong, nonatomic) id detailItem;
@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;
@property (strong, nonatomic) IBOutlet WheelView *wheelView;
@property (strong, nonatomic) PhotoAlbum *photoAlbum;

@end
