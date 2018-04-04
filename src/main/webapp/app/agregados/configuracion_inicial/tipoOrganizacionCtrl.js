app.controller("tipoOrganizacionCtrl",["$scope","crud","modal", function ($scope,crud,modal){
        
    $scope.tipoOrganizaciones = [];
    $scope.nuevoTipoOrganizacion = {codigo:"",nombre:"",descripcion:"",estado:'A'};
    $scope.tipoOrganizacionSel = {};
    
    
    $scope.listarTipoOrganizaciones = function(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('tipoOrganizacion',1,'listarTipoOrganizaciones');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las funciones de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.tipoOrganizaciones = data.data;
        },function(data){
            console.info(data);
        });
    };    
    $scope.agregarTipoOrganizacion = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('tipoOrganizacion',1,'insertarTipoOrganizacion');
        request.setData($scope.nuevoTipoOrganizacion);
        
        crud.insertar("/configuracionInicial",request,function(response){
            
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //recuperamos las variables que nos envio el servidor
                $scope.nuevoTipoOrganizacion.tipoOrganizacionID = response.data.tipoOrganizacionID;
                $scope.nuevoTipoOrganizacion.fecha = response.data.fecha;
                
                //insertamos el elemento a la lista
                $scope.tipoOrganizaciones.push($scope.nuevoTipoOrganizacion);                
                //reiniciamos las variables
                $scope.nuevoTipoOrganizacion = {codigo:"",nombre:"",descripcion:"",icono:"",estado:'A'};
                //cerramos la ventana modal
                $('#modalNuevo').modal('hide');
            }            
        },function(data){
            console.info(data);
        });
        
    };
    $scope.eliminarTipoOrganizacion = function(i,idDato,nomUsu){
        
        modal.mensajeConfirmacion($scope,"seguro que desea eliminar el tipo de organizacion",function(){
            
            var request = crud.crearRequest(nomUsu,"web");
            request.setCmd('tipoOrganizacion',1,'eliminarTipoOrganizacion');
            request.setData({tipoOrganizacionID:idDato});

            crud.eliminar("/configuracionInicial",request,function(response){

                modal.mensaje("CONFIRMACION",response.responseMsg);
                if(response.responseSta)
                    $scope.tipoOrganizaciones.splice(i,1);

            },function(data){
                console.info(data);
            });
            
        });
        
        
        
    };
    $scope.prepararEditar = function(i,t){
        $scope.tipoOrganizacionSel = JSON.parse(JSON.stringify(t));
        $scope.tipoOrganizacionSel.i = i;
        $('#modalEditar').modal('show');
    };
    $scope.editarTipoOrganizacion = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('tipoOrganizacion',1,'actualizarTipoOrganizacion');
        request.setData($scope.tipoOrganizacionSel);
                
        crud.actualizar("/configuracionInicial",request,function(response){
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){                
                //actualizando
                $scope.tipoOrganizaciones[$scope.tipoOrganizacionSel.i] = $scope.tipoOrganizacionSel;
                $('#modalEditar').modal('hide');
            }
        },function(data){
            console.info(data);
        });
    };
    $scope.eliminarTipoOrganizaciones = function(){
            
        
    };
    
    
}]);