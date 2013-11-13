//
//  HypnoAppDelegate.m
//  HypnoTime
//
//  Created by Tom LePage on 9/26/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "HypnoAppDelegate.h"
#import "HypnosisViewController.h"
#import "TimeViewController.h"

@implementation HypnoAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];

    HypnosisViewController *hvc = [[HypnosisViewController alloc] init];
    TimeViewController *tvc = [[TimeViewController alloc] init];
    UITabBarController *tabBarController = [[UITabBarController alloc] init];
    
    NSArray *viewControllers = [NSArray arrayWithObjects:hvc, tvc, nil];
    [tabBarController setViewControllers:viewControllers];
    
    [[self window] setRootViewController:tabBarController];
    
    self.window.backgroundColor = [UIColor whiteColor];
    [self.window makeKeyAndVisible];
    return YES;
}

@end
