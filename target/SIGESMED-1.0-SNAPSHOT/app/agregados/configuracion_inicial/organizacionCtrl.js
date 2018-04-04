app.controller("organizacionCtrl",["$scope","crud","modal", function ($scope,crud,modal){
        
    $scope.organizaciones = [];
    $scope.tipoOrganizaciones = [];
    $scope.nuevaOrganizacion = {codigo:"",nombre:"",alias:"",descripcion:"",direccion:"",estado:'A',tipoOrganizacionID:"0",organizacionPadreID:"0"};
    $scope.organizacionSel = {};
    
    
    $scope.listarOrganizaciones = function(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('organizacion',1,'listarOrganizaciones');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las organizaciones de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.organizaciones = data.data;
        },function(data){
            console.info(data);
        });
    };
    $scope.agregarOrganizacion = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('organizacion',1,'insertarOrganizacion');
        request.setData($scope.nuevaOrganizacion);
        
        crud.insertar("/configuracionInicial",request,function(response){
            
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //recuperamos las variables que nos envio el servidor
                $scope.nuevaOrganizacion.organizacionID = response.data.organizacionID;
                $scope.nuevaOrganizacion.fecha = response.data.fecha;
                
                //insertamos el elemento a la lista
                $scope.organizaciones.push($scope.nuevaOrganizacion);
                //reiniciamos las variables
                $scope.nuevaOrganizacion = {codigo:"",nombre:"",alias:"",descripcion:"",direccion:"",estado:'A',tipoOrganizacionID:"0",organizacionPadreID:"0"};
                //cerramos la ventana modal
                $('#modalNuevo').modal('hide');
            }            
        },function(data){
            console.info(data);
        });
        
    };
    $scope.eliminarOrganizacion = function(i,idDato,nomUsu){
        
        modal.mensajeConfirmacion($scope,"seguro que desea eliminar la organizacion",function(){
            
            var request = crud.crearRequest(nomUsu,"web");
            request.setCmd('organizacion',1,'eliminarOrganizacion');
            request.setData({organizacionID:idDato});

            crud.eliminar("/configuracionInicial",request,function(response){

                modal.mensaje("CONFIRMACION",response.responseMsg);
                if(response.responseSta)
                    $scope.organizaciones.splice(i,1);

            },function(data){
                console.info(data);
            });
            
        });
        
        
        
    };
    $scope.prepararEditar = function(i,t){
        $scope.organizacionSel = JSON.parse(JSON.stringify(t));
        $scope.organizacionSel.i = i;
        $('#modalEditar').modal('show');
    };
    $scope.editarOrganizacion = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('organizacion',1,'actualizarOrganizacion');
        request.setData($scope.organizacionSel);
                
        crud.actualizar("/configuracionInicial",request,function(response){
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){                
                //actualizando
                $scope.organizaciones[$scope.organizacionSel.i] = $scope.organizacionSel;
                $('#modalEditar').modal('hide');
            }
        },function(data){
            console.info(data);
        });
    };
    
    listarTipoOrganizaciones ();
    
    function listarTipoOrganizaciones (){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('tipoOrganizacion',1,'listarTipoOrganizaciones');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las organizaciones de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.tipoOrganizaciones  = data.data;
        },function(data){
            console.info(data);
        });
    }; 
    
    
}]);