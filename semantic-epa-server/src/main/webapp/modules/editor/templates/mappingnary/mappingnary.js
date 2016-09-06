angular.module('streamPipesApp')
    .directive('mappingPropertyNary', function ($interval) {
        return {
            restrict : 'E',
            templateUrl : 'modules/editor/templates/mappingnary/mappingnary.tmpl.html',
            scope : {
                staticProperty : "="
            },
            link: function (scope) {

                scope.toggle = function(property, staticProperty) {
                    if (scope.exists(property, staticProperty)) {
                        remove(property, staticProperty);
                    } else {
                        add(property, staticProperty);
                    }
                }

                scope.exists = function(property, staticProperty) {
                    if (!staticProperty.properties.mapsTo) return false;
                    return staticProperty.properties.mapsTo.indexOf(property.properties.elementId) > -1;
                }

                var add = function(property, staticProperty) {
                    if (!staticProperty.properties.mapsTo) {
                        staticProperty.properties.mapsTo = [];
                    }
                    staticProperty.properties.mapsTo.push(property.properties.elementId);
                }

                var remove = function(property, staticProperty) {
                    var index = staticProperty.properties.mapsTo.indexOf(property.properties.elementId);
                    staticProperty.properties.mapsTo.splice(index, 1);
                }
                
            }
        }

    });