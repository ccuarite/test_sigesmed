app.controller("tipoTramiteCtrl",["$scope","crud","modal", function ($scope,crud,modal){
    
    $scope.tipoOrganizaciones = [];
    $scope.areas = [];
    
    //Implenetacion del controlador
    $scope.tiposTramites = [];//arreglo donde estan todos los tipos de tramites
    //variable que servira para crear un nuevo tipo de tramite
    $scope.tipoTramite = {codigo:"",nombre:"",descripcion:"",tipo:true,costo:"0.0",duracion:0,tipoOrganizacionID:0};
    //variable temporal, para la seleccion de un tramite
    $scope.tipoTramiteSel = {};
    
    //varible para la creacion de un nuevo requisito
    $scope.requisito = {descripcion:"",archivo:{}};
    $scope.requisitoSel = {descripcion:"",archivo:{}};
    //lista de requisitos del tramite
    $scope.requisitos = [];
    
    //varible para la creacion de una ruta
    $scope.ruta = {areaOriID:0,areaOri:"",areaDesID:0,areaDes:"",descripcion:""};
    $scope.rutaSel = {areaOriID:0,areaOri:"",areaDesID:0,areaDes:"",descripcion:""};
    //lista de rutas del tramite
    $scope.rutas = [];
    
    $scope.listarTiposTramites = function(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('tipoTramite',1,'listarTipoTramite');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las funciones de exito y error
        crud.listar("/tramiteDocumentario",request,function(data){
            $scope.tiposTramites = data.data;
        },function(data){
            console.info(data);
        });
    };    
    $scope.agregarTipoTramite = function(){
        
        var request = crud.crearRequest("admin","web");
        request.setCmd('tipoTramite',1,'insertarTipoTramite');
        
        //if($scope.requisitos.length>0)
        $scope.tipoTramite.requisitos = $scope.requisitos;
        
        //if($scope.rutas.length>0)
        $scope.tipoTramite.rutas = $scope.rutas;
        
        request.setData($scope.tipoTramite);
        
        crud.insertar("/tramiteDocumentario",request,function(response){
            
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //recuperamos las variables que nos envio el servidor
                $scope.tipoTramite.tipoTramiteID = response.data.tipoTramiteID;
                $scope.tipoTramite.codigo = response.data.codigo;
                
                //insertamos el elemento a la lista
                $scope.tiposTramites.push($scope.tipoTramite);
                //reiniciamos las variables
                $scope.tipoTramite = {codigo:"",nombre:"",descripcion:"",tipo:true,costo:"0.0",duracion:0,tipoOrganizacionID:0};
                $scope.requisitos = [];
                $scope.rutas = [];
                //cerramos la ventana modal
                $('#modalNuevoTramite').modal('hide');
            }            
        },function(data){
            console.info(data);
        });
        
    };
    $scope.eliminarTipoTramite = function(i,idTipoTramite){
        
        modal.mensajeConfirmacion($scope,"seguro que desea eliminar este registro",function(){
        
            var request = crud.crearRequest("admin","web");
            request.setCmd('tipoTramite',1,'eliminarTipoTramite');
            request.setData({tipoTramiteID:idTipoTramite});

            crud.eliminar("/tramiteDocumentario",request,
            function(response){
                modal.mensaje("CONFIRMACION",response.responseMsg);
                if(response.responseSta)
                    $scope.tiposTramites.splice(i,1);

            },function(data){
            });
        });
    };
    $scope.prepararEditar = function(i,t){
        $scope.tipoTramiteSel = JSON.parse(JSON.stringify(t));
        $scope.tipoTramiteSel.i = i;
        $('#modalEditarTramite').modal('show');
    };
    $scope.editarTipoTramite = function(){
        
        var request = crud.crearRequest("admin","web");
        request.setCmd('tipoTramite',1,'actualizarTipoTramite');
        request.setData($scope.tipoTramiteSel);
                
        crud.actualizar("/tramiteDocumentario",request,function(response){
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //actualizando
                $scope.tiposTramites[$scope.tipoTramiteSel.i] = $scope.tipoTramiteSel;
                $('#modalEditarTramite').modal('hide');
            }
        },function(data){
            console.info(data);
        });
    };
    $scope.eliminarTiposTramites = function(){
        modal.mensaje("CONFIRMACION","no se puede eliminar tipos de tramites");
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
    
    $scope.tipo = function(t){
        if(t)
            return "externo";
        return "interno";
    };
    
    //requisitos
    $scope.agregarRequisito = function(){
        
        if($scope.requisito.descripcion =="" )
            return;
        $scope.requisitos.push($scope.requisito);
        $scope.requisito = {descripcion:"",archivo:{}};
    };    
    $scope.eliminarRequisito = function(i){
        $scope.requisitos.splice(i,1);
    };
    $scope.agregarRequisitoSel = function(){
        
        if($scope.requisitoSel.descripcion =="" )
            return;
        $scope.tipoTramiteSel.requisitos.push($scope.requisito);
        $scope.requisitoSel = {descripcion:"",archivo:{}};
    };    
    $scope.eliminarRequisitoSel = function(i){
        $scope.tipoTramiteSelrequisitos.splice(i,1);
    };
    
    //rutas
    $scope.agregarRuta = function(){
        
        if($scope.ruta.areaOriID ==0 || $scope.ruta.areaDesID ==0 )
            return;
        $scope.ruta.areaOri = buscarContenido($scope.areas,"areaID","nombre",$scope.ruta.areaOriID);
        $scope.ruta.areaDes = buscarContenido($scope.areas,"areaID","nombre",$scope.ruta.areaDesID);
        
        $scope.rutas.push($scope.ruta);
        $scope.ruta = {areaOriID:0,areaOri:"",areaDesID:0,areaDes:"",descripcion:""};
    };    
    $scope.eliminarRuta = function(i){
        $scope.rutas.splice(i,1);
    };
    
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
}]);