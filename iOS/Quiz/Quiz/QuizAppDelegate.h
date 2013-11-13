//
//  QuizAppDelegate.h
//  Quiz
//
//  Created by Tom LePage on 9/25/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@class QuizViewController;

@interface QuizAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) QuizViewController *viewController;

@end
