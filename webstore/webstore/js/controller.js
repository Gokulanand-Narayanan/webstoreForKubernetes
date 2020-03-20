/**
 * jVoiDView controller
 * 
 * @author Shajir
 * @version 1.0
 */

 var coreDomainURL = 'http://bigstore.eginnovations.com';
 var coreDomainContext = '/bigstoreCoreEngine';
 
var app = angular.module('jvoid', ['ngRoute'], function($httpProvider) {
  // Use x-www-form-urlencoded Content-Type
  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
  var param = function(obj) {
    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
      
    for(name in obj) {
      value = obj[name];
        
      if(value instanceof Array) {
        for(i=0; i<value.length; ++i) {
          subValue = value[i];
          fullSubName = name + '[' + i + ']';
          innerObj = {};
          innerObj[fullSubName] = subValue;
          query += param(innerObj) + '&';
        }
      }
      else if(value instanceof Object) {
        for(subName in value) {
          subValue = value[subName];
          fullSubName = name + '[' + subName + ']';
          innerObj = {};
          innerObj[fullSubName] = subValue;
          query += param(innerObj) + '&';
        }
      }
      else if(value !== undefined && value !== null)
        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
    }
      
    return query.length ? query.substr(0, query.length - 1) : query;
  };
 
  // Override $http service's default transformRequest
  $httpProvider.defaults.transformRequest = [function(data) {
    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
  }];
});

var productsLoaded;
var categoriesLoaded;
var cartLoaded;
var cartId = -1;
//var item = -1;
var loginedCustomer = {};

app.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/products', {
		templateUrl: 'products.html',
		controller: 'productsCtrl'
      }).
      when('/product/:id', {
		templateUrl: 'product.html',
		controller: 'productCtrl'
      }).
      when('/cart', {
      	templateUrl: 'cart.html',
		controller: 'cartCtrl'
      }).
      when('/checkout', {
      	templateUrl: 'checkout.html',
		controller: 'checkoutCtrl'
      }).
      when('/usercheckout', {
      	templateUrl: 'usercheckout.html',
		controller: 'usercheckoutCtrl'
      }).
      when('/categories', {
      	templateUrl: 'category.html',
		controller: 'categoryCtrl'
      }).
      when('/category/:id/:name', {
      	templateUrl: 'products.html',
		controller: 'categoryProdCtrl'
      }).
      when('/signup', {
      	templateUrl: 'signup.html',
		controller: 'signupCtrl'
      }).
      otherwise({
		redirectTo: '/products'
      });
}]);

app.directive('modal', function () {
  return {
    template: '<div class="modal fade">' + 
        '<div class="modal-dialog">' + 
          '<div class="modal-content">' + 
            '<div class="modal-header">' + 
              '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' + 
              '<h4 class="modal-title">{{ title }}</h4>' + 
            '</div>' + 
            '<div class="modal-body" ng-transclude></div>' + 
          '</div>' + 
        '</div>' + 
      '</div>',
    restrict: 'E',
    transclude: true,
    replace:true,
    scope:true,
    link: function postLink(scope, element, attrs) {
      scope.title = attrs.title;

      scope.$watch(attrs.visible, function(value){
        if(value == true)
          $(element).modal('show');
        else
          $(element).modal('hide');
      });

      $(element).on('shown.bs.modal', function(){
        scope.$apply(function(){
          scope.$parent[attrs.visible] = true;
        });
      });

      $(element).on('hidden.bs.modal', function(){
        scope.$apply(function(){
          scope.$parent[attrs.visible] = false;
        });
      });
    }
  };
});
  
app.controller('navigationController', function($scope, $http, $sce, $location) {
	$scope.showModal = false;
	if(loginedCustomer.id>0){
		$scope.logined = true;
		$scope.user = loginedCustomer;
	}
	else{
		$scope.logined = false;
	}
		
	$scope.$on("loginSuccessEvent", function (event, args) {
		if(loginedCustomer.id>0){
			$scope.logined = true;
			$scope.user = loginedCustomer;
		}
	});
	
    $scope.signInButton = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    $scope.submitAction = function(){
    	var email = document.getElementById("email").value;
        var pass = document.getElementById("password").value;
        $scope.showModal = false;
        var login = {};
        login.email = email;
        login.password = pass;
		
		$http.post(coreDomainURL+coreDomainContext+'/login', "params="+JSON.stringify(login)+"&email="+email+"&pass="+pass)
		.success(function(response) {
		    response.country = JSON.parse(response.country);	
			if(response.state){
				response.state = JSON.parse(response.state);
			}
	 		loginedCustomer = response;
			console.log('sssssssssssssssssssssssssssssss',loginedCustomer)
	 		$scope.user = loginedCustomer;
			console.log(loginedCustomer);
	 		$location.path('/products'); 
			$scope.logined = true;
		})
		.error(function() {
	 		alert("Error: Try again");
		});
    };
    
    $scope.forgetPassWordAction = function(){
    	$scope.showModal = false;
    	$scope.showModalpass = !$scope.showModalpass;
    }
    
    $scope.submitEmailAction = function(){
    	var email = document.getElementById("emailpass").value;
        $scope.showModalpass = false;
        var login = {};
        login.email = email;
		
		$http.post(coreDomainURL+coreDomainContext+'/password-reset', "params="+JSON.stringify(login))
		.success(function(response) {
		    if(response.status == 1){
		 		alert("New password sent to your registered email successfully.");
	 		}
	 		else{
	 			alert("Email not registered.");
	 		}
		})
		.error(function() {
	 		alert("Error. Please try again.");
		});
    };
    
    $scope.signupAction = function(){
        $scope.showModal = false;
        $location.path('/signup');
    };
    
    $scope.accountAction = function(){
        $scope.showModal = false;
        $location.path('/signup');
    };
    
    $scope.logoutAction = function(){
        $location.path('/home');
        loginedCustomer = {};
        $scope.logined = false;
    };
	var a = new Date().getTime();
    //console.log("About to call categories.... "+new Date().getTime());
	$http.get(coreDomainURL+coreDomainContext+"/categories")
 	.success(function(response) {

		var end = new Date().getTime();
		
		console.log("Time diff "+(end -a));
		var catArray = response.categories;
		categoriesLoaded = catArray;
		var cats = sortOutAllCategories(catArray, 0);
	    $scope.values = cats;
	    
	    var str = prepareOutHtml(cats);
	    $scope.htmlString = $sce.trustAsHtml(str);
	})
	.error(function() {
 		console.log("navigationController error");
	});
});

function sortOutAllCategories(catArray, parentID){
	var cats = [];
	catArray.forEach(function(cat) 
    {
    	if(cat.parentId == parentID){
			var out = sortOutAllCategories(catArray, cat.id);	
    		var outJson = {};
    		outJson.name = cat.categoryName;
    		outJson.id = cat.id;
    		outJson.values = out;    
		    cats.push(outJson);
		    if(cats.length>cat.position){
		    	var temp = cats[cat.position-1];
		    	cats[cat.position-1] = outJson;
		    	cats[cats.length-1] = temp;
		    }
    	}
    });
    
    return cats;
}

function prepareOutHtml(catArray){
	var outHtmlStr = "";
	catArray.forEach(function(cat) {
    	if(cat.values.length>0){
    		outHtmlStr += "<li class=\"dropdown-submenu\">";
    		outHtmlStr += "<a href=\"#category/"+cat.id+"/"+cat.name+"\">"+cat.name+"</a>";
    		outHtmlStr += "<ul class=\"dropdown-menu\">";
    		outHtmlStr += prepareOutHtml(cat.values);
    		outHtmlStr += "</ul></li>";
    	}
    	else{
    		outHtmlStr += "<li><a href=\"#category/"+cat.id+"/"+cat.name+"\">"+cat.name+"</a></li>";
    	}
    });
	return outHtmlStr;
}

app.controller('productsCtrl', function($scope, $http) {
	$scope.title = "Products";
	$scope.detTitle = "Available Products";
    
 	$http.get(coreDomainURL+coreDomainContext+"/products")
 	.success(function(response) {
 		$scope.products = response.products;
 		productsLoaded = response.products;
	})
	.error(function() {
 		console.log("productsCtrl error");
	});
});

app.controller('productCtrl', function($scope, $http, $routeParams) {
	$scope.title = "Product";
	$scope.detTitle = productsLoaded[$routeParams.id].name;
	$scope.product = productsLoaded[$routeParams.id];
	$scope.orderNow = function(){
		$http.get(coreDomainURL+coreDomainContext+"/addtocart?cartId="+cartId+"&prodId="+$scope.product.id)
	 	.success(function(response) {
	 		cartId = response.cartId;
			//em = response.sku;
	 		alert($scope.product.name+" added to cart");
		})
		.error(function() {
	 		alert("Error: Please try again");
		});
	};
});

app.controller('cartCtrl', function($scope, $http, $location, $route) {
	$scope.title = "Cart";
	$scope.detTitle = "Your Cart";
	$scope.emptyCart = true;
	if(cartId!=-1){
		$http.get(coreDomainURL+coreDomainContext+"/viewcart?cartId="+cartId)  
	 	.success(function(response) {
	 		cartLoaded = response;
	 		$scope.items = response.items;
	 		$scope.total = response.total;
	 		$scope.emptyCart = false;
		})
		.error(function() {
	 		alert("Error: Please try again");
		});
	}
	
	$scope.checkoutCart = function(){
		if(loginedCustomer.id>0){
			$location.path('/usercheckout');
		}
		else{
			$location.path('/checkout');
		}
	};
	
	$scope.clearCart = function(){
		$http.get(coreDomainURL+coreDomainContext+"/remove-cart?cartId="+cartId+"&prodId=-1")  
	 	.success(function(response) {
	 		cartId = -1;
			//item = -1;
	 		alert("Cart cleared successfully");
	 		$location.path('/products');  
		})
		.error(function() {
	 		alert("Error: Please try again");
		});
	};
	
	$scope.removeFromCart = function(id){
		$http.get(coreDomainURL+coreDomainContext+"/remove-cart?cartId="+cartId+"&prodId="+id)  
	 	.success(function(response) {
	 		alert("Item removed from cart");
	 		$route.reload(); 
		})
		.error(function() {
	 		alert("Error: Please try again");
		});
	};
});

app.controller('checkoutCtrl', function($scope, $http, $location) {
	$scope.title = "Checkout";
	$scope.detTitle = "Please Checkout your Cart";
	$scope.onlyNumbers = /^[0-9]+$/;
	$scope.bCountryNotSelected = true;
	$scope.sCountryNotSelected = true;
	var checkedAction = false;
	$scope.bStateNotSelected = false;
	$scope.sStateNotSelected = false;
	$scope.payment = "COD";
	
	///Testing
	$scope.user = {};
	$scope.billing = {};
//	$scope.user.firstName = "Shajir";
//	$scope.user.lastName = "K";
//	$scope.user.company = "Schogini";
//	$scope.user.email = "shajirk@schogini.com";
//	
//	$scope.billing.address = "PTP 292";
//	$scope.billing.address2 = "PTP Nagar";
//	$scope.billing.city = "Trivandrum"
//	$scope.billing.zip = "695038";
//	$scope.billing.phone = "9946269117";
//	$scope.billing.fax = "1234567890";
	////
	
	$http.get('json/countries.json')
       .then(function(res){
          $scope.countries = res.data;               
        });
        
    $scope.onBDropDownAction = function() {
    	if($scope.billing.country){
    		$scope.bCountryNotSelected = false;
    		
    		if($scope.billing.country.filename){
	    		$scope.isBStates = true;
	    		$scope.bStateNotSelected = true;
		    	$http.get('json/countries/'+$scope.billing.country.filename+'.json')
			       .then(function(res){
			        	$scope.states = res.data;  
			        });
	    	}
	    	else{
	    		$scope.isBStates = false;
	    	}
    	}
    	else{
    		$scope.isBStates = false;
    		$scope.bStateNotSelected = false;
    		$scope.bCountryNotSelected = true;
    	}
    	
    	if(checkedAction){
        	$scope.isSStates = $scope.isBStates;
        	$scope.sCountryNotSelected = $scope.bCountryNotSelected;
        	$scope.sStateNotSelected = $scope.bStateNotSelected;
        }
    };
    
    $scope.onBStateDropDownAction = function() {
    	if($scope.billing.state){
    		$scope.bStateNotSelected = false;
    	}
    	else{
    		$scope.bStateNotSelected = true;
    	}
    	
    	if(checkedAction){
        	$scope.isSStates = $scope.isBStates;
        	$scope.sCountryNotSelected = $scope.bCountryNotSelected;
        	$scope.sStateNotSelected = $scope.bStateNotSelected;
        }
    };
    
    $scope.onSDropDownAction = function() {
    	if($scope.shipping.country){
    		$scope.sCountryNotSelected = false;
    		
    		if($scope.shipping.country.filename){
	    		$scope.isSStates = true;
	    		$scope.sStateNotSelected = true;
		    	$http.get('json/countries/'+$scope.shipping.country.filename+'.json')
			       .then(function(res){
			        	$scope.states = res.data;  
			        });
	    	}
	    	else{
	    		$scope.isSStates = false;
	    	}
    	}
    	else{
    		$scope.isSStates = false;
    		$scope.sStateNotSelected = false;
    		$scope.sCountryNotSelected = true;
    	}
    };
    
    $scope.onSStateDropDownAction = function() {
    	if($scope.shipping.state){
    		$scope.sStateNotSelected = false;
    	}
    	else{
    		$scope.sStateNotSelected = true;
    	}
    };

	$scope.onCHeckBoxAction = function() {
        if($scope.address.checked){
        	$scope.isSStates = $scope.isBStates;
        	$scope.sCountryNotSelected = $scope.bCountryNotSelected;
        	$scope.sStateNotSelected = $scope.bStateNotSelected;
        	$scope.shipping = $scope.billing;
        	
        	checkedAction = true;
        }
        else{
        	checkedAction = false;
        }
    };
    
    $scope.submit = function() {
    	var checkoutJson = {};
    	checkoutJson.cartId = cartId; 
    	checkoutJson.cart = cartLoaded;
    	$scope.user.id = -1;
   		checkoutJson.user = $scope.user;
   		checkoutJson.billing = $scope.billing;
   		checkoutJson.shipping = $scope.shipping;
   		checkoutJson.checkoutMethod = $scope.payment;
   		checkoutJson.checkoutComment = $scope.comment; 
   				
		$http.post(coreDomainURL+coreDomainContext+'/checkout-cart', "params="+JSON.stringify(checkoutJson))
		.success(function(response) {
		    if(response.orderId){
		 		alert("You successfully checkout your order. Your order id is "+response.orderId);
		 		cartLoaded = null;
				cartId = -1;
				//item = -1;
		 		$location.path('/products'); 
	 		}
	 		else{
	 			alert("Checkout error. Please try again.");
	 		}
		})
		.error(function() {
	 		alert("Checkout error. Please try again.");
		});
    };
});

app.controller('usercheckoutCtrl', function($scope, $http, $location) {
	$scope.title = "Checkout";
	$scope.detTitle = "Please Checkout your Cart";
	$scope.payment = "COD";
	$scope.user = loginedCustomer;
	if($scope.user.state){
		$scope.isSStatesDisplay = true;
	}
	$scope.checked = true;
	$scope.onlyNumbers = /^[0-9]+$/;
	$scope.countryNotSelected = true;
	$scope.stateNotSelected = false;
	$http.get('json/countries.json')
       .then(function(res){
          $scope.countries = res.data;               
        });

    $scope.onSDropDownAction = function() {
    	if($scope.shipping.country){
    		$scope.countryNotSelected = false;
    		
    		if($scope.shipping.country.filename){
	    		$scope.isSStates = true;
	    		$scope.stateNotSelected = true;
		    	$http.get('json/countries/'+$scope.shipping.country.filename+'.json')
			       .then(function(res){
			        	$scope.states = res.data;  
			        });
	    	}
	    	else{
	    		$scope.isSStates = false;
	    	}
    	}
    	else{
    		$scope.isSStates = false;
    		$scope.stateNotSelected = false;
    		$scope.countryNotSelected = true;
    	}
    };

    $scope.onSStateDropDownAction = function() {
    	if($scope.shipping.state){
    		$scope.stateNotSelected = false;
    	}
    	else{
    		$scope.stateNotSelected = true;
    	}
    };
    
    $scope.submit = function() {
    	var checkoutJson = {};
    	checkoutJson.cartId = cartId; 
    	checkoutJson.cart = cartLoaded;
    	var checkoutUser = {};
    	checkoutUser.id = $scope.user.id;
    	checkoutUser.firstName = $scope.user.firstName;
    	checkoutUser.lastName = $scope.user.lastName;
    	checkoutUser.company = $scope.user.company;
    	checkoutUser.email = $scope.user.email;  	
   		checkoutJson.user = checkoutUser;
   		var billing = {};
   		billing.address = $scope.user.streetAddress1;
   		billing.address2 = $scope.user.streetAddress2+", "+$scope.user.streetAddress3;
   		billing.city = $scope.user.city;
		billing.state = $scope.user.state;
   		billing.country = $scope.user.country;
   		billing.zip = $scope.user.postalCode;
   		billing.phone = $scope.user.phone;
   		billing.fax = $scope.user.fax;
   		checkoutJson.billing = billing;
   		if($scope.checked){
   			checkoutJson.shipping = billing;
   		}
   		else{
   			checkoutJson.shipping = $scope.shipping;
   		}
   		checkoutJson.checkoutMethod = $scope.payment;
   		checkoutJson.checkoutComment = $scope.comment; 
   		
   		$http.post(coreDomainURL+coreDomainContext+'/checkout-cart', "params="+JSON.stringify(checkoutJson))
		.success(function(response) {
		    if(response.orderId){
		 		alert("You successfully checkout your order. Your order id is "+response.orderId);
		 		cartLoaded = null;
				cartId = -1;
				item = -1;
		 		$location.path('/products'); 
	 		}
	 		else{
	 			alert("Checkout error. Please try again.");
	 		}
		})
		.error(function() {
	 		alert("Checkout error. Please try again.");
		});
    }
});

app.controller('categoryCtrl', function($scope, $http) {
	$scope.title = "Categories";
	$scope.detTitle = "Please select your category";
	$scope.categories = categoriesLoaded;
});

app.controller('categoryProdCtrl', function($scope, $http, $routeParams) {
	$scope.title = "Products";
	$scope.detTitle = "Available Products in "+$routeParams.name;
	$http.get(coreDomainURL+coreDomainContext+"/products-by-cat?catId="+$routeParams.id)
 	.success(function(response) {
 		$scope.products = response.products;
 		productsLoaded = response.products;
	})
	.error(function() {
 		console.log("productsCtrl error");
	});
});

app.controller('signupCtrl', function($scope, $http, $location, $rootScope) {
	$scope.onlyNumbers = /^[0-9]+$/;
	$scope.countryNotSelected = true;
	$scope.stateNotSelected = false;
	$scope.newCustomer = true;
	
	$scope.user = {};
	$scope.user.gender = "male";
	$scope.user.customerGroup = 3;
	$scope.user.type = "Customer";
	///Testing
	$scope.user.prefix = "Mr";
// $scope.user.firstName = "Shajir";
	$scope.user.middleName = "E";
	$scope.user.lastName = "G";
	$scope.user.company = "Jax-London";
//	$scope.user.email = "shajirk@schogini.com";
	$scope.user.dateOfBirth = "09-10-2018";
	$scope.user.taxNumber = "TAX1234";
	$scope.user.password = "Password123";	
	$scope.user.streetAddress1 = "52 Upper Street ";
	$scope.user.streetAddress2 = " Islington ";
	$scope.user.streetAddress3 = "";
	$scope.user.city = "London"
	$scope.user.postalCode = "N1 0QH";
	$scope.user.phone = "9999999999";
	$scope.user.fax = "1234567890";
	////
	
	if(loginedCustomer.id){
		$scope.newCustomer = false;
		$scope.user = loginedCustomer;
			        
		$scope.title = $scope.user.firstName;
		$scope.detTitle = "Update your deatils";
		$scope.countryNotSelected = false;
		$scope.stateNotSelected = false;
						
		if($scope.user.state){
			$scope.isSStates = true;	
			$http.get('json/countries/'+$scope.user.country.filename+'.json')
		       .then(function(res){
		        	$scope.states = res.data;  
		        });
		}
	}
	else{
		$scope.title = "New User";
		$scope.detTitle = "Please enter your deatils";
		$scope.newCustomer = true;
		$scope.user.id = -1;
	}
	
	$http.get('json/countries.json')
       .then(function(res){
          $scope.countries = res.data;               
        });

    $scope.onSDropDownAction = function() {
    	if($scope.user.country){
    		$scope.countryNotSelected = false;
    		
    		if($scope.user.country.filename){
	    		$scope.isSStates = true;
	    		$scope.stateNotSelected = true;
		    	$http.get('json/countries/'+$scope.user.country.filename+'.json')
			       .then(function(res){
			        	$scope.states = res.data;  
			        });
	    	}
	    	else{
	    		$scope.isSStates = false;
	    	}
    	}
    	else{
    		$scope.isSStates = false;
    		$scope.stateNotSelected = false;
    		$scope.countryNotSelected = true;
    	}
    };

    $scope.onSStateDropDownAction = function() {
    	if($scope.user.state){
    		$scope.stateNotSelected = false;
    	}
    	else{
    		$scope.stateNotSelected = true;
    	}
    };

    $scope.submit = function() {
		$scope.user.country = {"name":"United Kingdom","code":"GB","filename":"united-kingdom"};
		$scope.user.state = {"name":"London, City of","code":"GB-LND"};
        var email = $scope.user.email;
		$http.post(coreDomainURL+coreDomainContext+'/sign-up', "params="+JSON.stringify($scope.user)+"&email="+email)
		.success(function(response) {
		    if(response.status == 1){
		    	if($scope.newCustomer){
		    		alert("You successfully registered to BigStore. Your customer id is "+response.id);
		    	}
		 		else{
		 			alert("Your details updated successfully.");
		 		}
		 		$scope.user.id = response.id;
		 		loginedCustomer = $scope.user;
		 		$location.path('/products'); 
				$rootScope.$broadcast("loginSuccessEvent", {userId:response.id});
	 		}
	 		else{
	 			if($scope.newCustomer){
	 				alert("Registration error. Please try again.");
		    	}
	 			else{
	 				alert("Updation error. Please try again.");
	 			}
	 		}
		})
		.error(function() {
	 		alert("Error. Please try again.");
		});
    };
});
