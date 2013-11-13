//
//  HappinessViewController.h
//  Happiness
//
//  Created by Tom LePage on 7/16/12.
//  Copyright (c) 2012 Cash America Intl. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SplitViewBarButtonItemPresenter.h"

@interface HappinessViewController : UIViewController <SplitViewBarButtonItemPresenter>

@property (nonatomic) int happiness; // (model) 0 is sad; 100 is happy
@end
