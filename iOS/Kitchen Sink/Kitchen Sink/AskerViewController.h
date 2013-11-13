//
//  AskerViewController.h
//  Kitchen Sink
//
//  Created by Tom LePage on 7/26/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@class AskerViewController;

@protocol AskerViewControllerDelegate <NSObject>

- (void)askerViewController:(AskerViewController *)sender
             didAskQuestion:(NSString *)question
               andGotAnswer:(NSString *)answer;
@end

@interface AskerViewController : UIViewController

@property (nonatomic, copy) NSString *question;
@property (nonatomic, copy) NSString *answer;
@property (nonatomic, weak) id <AskerViewControllerDelegate> delegate;
@end
