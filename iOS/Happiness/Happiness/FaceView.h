//
//  FaceView.h
//  Happiness
//
//  Created by Tom LePage on 7/16/12.
//  Copyright (c) 2012 Cash America Intl. All rights reserved.
//

#import <UIKit/UIKit.h>

@class FaceView; // forward reference

@protocol FaceViewDataSource

- (float) smileForFaceView:(FaceView *)sender;  // this will only work here if you have a forward reference

@end

@interface FaceView : UIView

@property CGFloat scale;

- (void) pinch:(UIPinchGestureRecognizer *)pinch; // public

@property (nonatomic, weak) IBOutlet id <FaceViewDataSource> dataSource;

@end
