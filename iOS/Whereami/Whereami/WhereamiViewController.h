//
//  WhereamiViewController.h
//  Whereami
//
//  Created by Tom LePage on 9/25/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import <MapKit/MapKit.h>

@interface WhereamiViewController : UIViewController <CLLocationManagerDelegate,
                                                      MKMapViewDelegate,
                                                      UITextFieldDelegate>
{
    // CLLocationManager stuff
    CLLocationManager *locationManager;
    
    // MapKit stuff
    IBOutlet MKMapView *worldView;
    IBOutlet UIActivityIndicatorView *activityIndicator;
    IBOutlet UITextField *locationTitleField;
    
    // Segmented Controller
    IBOutlet UISegmentedControl *mapTypeSegmentedController;
}

- (void)findLocation;
- (void)foundLocation:(CLLocation *)loc;

- (IBAction)switchMapType:(id)sender;

@end
