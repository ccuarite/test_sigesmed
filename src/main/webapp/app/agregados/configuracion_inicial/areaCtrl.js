app.controller("areaCtrl",["$scope","crud","modal", function ($scope,crud,modal){
        
    $scope.areas = [];
    $scope.nuevaArea = {codigo:"",nombre:"",alias:"",estado:'A',organizacionID:"0",areaPadreID:"0"};
    $scope.areaSel = {};
    
    
    $scope.listarAreas = function(organizacionID){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('area',1,'listarAreasPorOrganizacion');
        request.setData({organizacionID:organizacionID});
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las areas de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.areas = data.data;
        },function(data){
            console.info(data);
        });
    };
    $scope.agregarArea = function(nomUsu,organizacionID){
        
        $scope.nuevaArea.organizacionID = organizacionID;
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('area',1,'insertarArea');        
        request.setData($scope.nuevaArea);
        
        crud.insertar("/configuracionInicial",request,function(response){
            
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //recuperamos las variables que nos envio el servidor
                $scope.nuevaArea.areaID = response.data.areaID;
                $scope.nuevaArea.areaPadre = buscarContenido($scope.areas,"areaID","nombre",$scope.nuevaArea.areaPadreID);
                
                //insertamos el elemento a la lista
                $scope.areas.push($scope.nuevaArea);
                //reiniciamos las variables
                $scope.nuevaArea = {codigo:"",nombre:"",alias:"",estado:'A',organizacionID:"0",areaPadreID:"0"};
                //cerramos la ventana modal
                $('#modalNuevo').modal('hide');
            }            
        },function(data){
            console.info(data);
        });
        
    };
    $scope.eliminarArea = function(i,idDato,nomUsu){
        
        modal.mensajeConfirmacion($scope,"seguro que desea eliminar la area",function(){
            
            var request = crud.crearRequest(nomUsu,"web");
            request.setCmd('area',1,'eliminarArea');
            request.setData({areaID:idDato});

            crud.eliminar("/configuracionInicial",request,function(response){

                modal.mensaje("CONFIRMACION",response.responseMsg);
                if(response.responseSta)
                    $scope.areas.splice(i,1);

            },function(data){
                console.info(data);
            });
            
        });
        
        
        
    };
    $scope.prepararEditar = function(i,t){
        $scope.areaSel = JSON.parse(JSON.stringify(t));
        $scope.areaSel.i = i;
        $('#modalEditar').modal('show');
    };
    $scope.editarArea = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('area',1,'actualizarArea');
        request.setData($scope.areaSel);
                
        crud.actualizar("/configuracionInicial",request,function(response){
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){                
                //actualizando
                $scope.areaSel.areaPadre = buscarContenido($scope.areas,"areaID","nombre",$scope.areaSel.areaPadreID);                
                $scope.areas[$scope.areaSel.i] = $scope.areaSel;
                $('#modalEditar').modal('hide');
            }
        },function(data){
            console.info(data);
        });
    };
    /*
    listarTipoAreas ();
    
    function listarTipoAreas (){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('tipoAreas',1,'listarTipoAreas');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las areas de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.tipoAreas  = data.data;
        },function(data){
            console.info(data);
        });
    }; 
    */
    
}]);