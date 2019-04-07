angular.module('app')
    .config([
        '$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
            $ocLazyLoadProvider.config({
                debug: true,
                events: true,
                modules: [
                    {
                        name: 'toaster',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/angularjs-toaster/toaster.css',
                            'resource/others/BeyondAdmin/lib/modules/angularjs-toaster/toaster.js'
                        ]
                    },
                    {
                        name: 'ui.select',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/angular-ui-select/select.css',
                            'resource/others/BeyondAdmin/lib/modules/angular-ui-select/select.js'
                        ]
                    },
                    {
                        name: 'ngTagsInput',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/ng-tags-input/ng-tags-input.js'
                        ]
                    },
                    {
                        name: 'daterangepicker',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/angular-daterangepicker/moment.js',
                            'resource/others/BeyondAdmin/lib/modules/angular-daterangepicker/daterangepicker.js',
                            'resource/others/BeyondAdmin/lib/modules/angular-daterangepicker/angular-daterangepicker.js'
                        ]
                    },
                    {
                        name: 'vr.directives.slider',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/angular-slider/angular-slider.min.js'
                        ]
                    },
                    {
                        name: 'minicolors',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/angular-minicolors/jquery.minicolors.js',
                            'resource/others/BeyondAdmin/lib/modules/angular-minicolors/angular-minicolors.js'
                        ]
                    },
                    {
                        name: 'textAngular',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/text-angular/textAngular-sanitize.min.js',
                            'resource/others/BeyondAdmin/lib/modules/text-angular/textAngular-rangy.min.js',
                            'resource/others/BeyondAdmin/lib/modules/text-angular/textAngular.min.js'
                        ]
                    },
                    {
                        name: 'ng-nestable',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/angular-nestable/jquery.nestable.js',
                            'resource/others/BeyondAdmin/lib/modules/angular-nestable/angular-nestable.js'
                        ]
                    },
                    {
                        name: 'angularBootstrapNavTree',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/angular-bootstrap-nav-tree/abn_tree_directive.js'
                        ]
                    },
                    {
                        name: 'ui.calendar',
                        files: [
                            'resource/others/BeyondAdmin/lib/jquery/fullcalendar/jquery-ui.custom.min.js',
                            'resource/others/BeyondAdmin/lib/jquery/fullcalendar/moment.min.js',
                            'resource/others/BeyondAdmin/lib/jquery/fullcalendar/fullcalendar.js',
                            'resource/others/BeyondAdmin/lib/modules/angular-ui-calendar/calendar.js'
                        ]
                    },
                    {
                        name: 'ngGrid',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/ng-grid/ng-grid.min.js',
                            'resource/others/BeyondAdmin/lib/modules/ng-grid/ng-grid.css'
                        ]
                    },
                    {
                        name: 'dropzone',
                        files: [
                            'resource/others/BeyondAdmin/lib/modules/angular-dropzone/dropzone.min.js',
                            'resource/others/BeyondAdmin/lib/modules/angular-dropzone/angular-dropzone.js'
                        ]
                    },
                    {
                        name: 'datetimepicker',
                        files: [
                            'resource/others/BeyondAdmin/lib/jquery/datetimepicker/jquery.datetimepicker.css',
                            'resource/others/BeyondAdmin/lib/jquery/datetimepicker/jquery.datetimepicker.js',
                            'resource/others/BeyondAdmin/lib/jquery/datetimepicker/angular.datetime.js'
                        ]
                    },
                    {
                        name: 'datatable',
                        files: [
								'resource/others/BeyondAdmin/lib/jquery/datatable/dataTables.bootstrap.css',
								'resource/others/BeyondAdmin/lib/jquery/datatable/dataTables.tableTools.min.js',
								'resource/others/BeyondAdmin/lib/jquery/datatable/dataTables.bootstrap.min.js',
								'resource/others/BeyondAdmin/lib/jquery/datatable/dataTables.bootstrap.pagination.js',
								'resource/others/BeyondAdmin/lib/jquery/datatable/ZeroClipboard.js',
								'resource/others/BeyondAdmin/lib/jquery/datatable/jquery.dataTables.min.js'
                        ]
                    },
                    {
                        name: 'jqgrid',
                        serie:true,
                        files: [
								'resource/others/BeyondAdmin/lib/jquery/jqgrid/css/ui.jqgrid-bootstrap.css',
								'resource/others/BeyondAdmin/lib/jquery/jqgrid/css/ui-lightness/jquery-ui-1.8.16.custom.css',
								'resource/others/BeyondAdmin/lib/jquery/jqgrid/css/ui.jqgrid.reset.css',
								'resource/others/BeyondAdmin/lib/jquery/jqgrid/js/jquery.jqGrid.js',
								'resource/others/BeyondAdmin/lib/jquery/jqgrid/js/i18n/grid.locale-cn.js',
								'resource/others/BeyondAdmin/lib/jquery/jqgrid/js/angular.jqGrid.js',
                        ]
                    },
                    {
                        name: 'messager',
                        files: [
								'resource/js/common/modules/messager.js'
                        ]
                    },
                    {
                        name: 'templateform',
                        files: [
								'resource/js/common/modules/templateform.js'
                        ]
                    },{
                    	name:'ui.tree',
                    	files: [
								'resource/others/BeyondAdmin/lib/modules/angular-ui-tree/angular-ui-tree.min.js',
								'resource/others/BeyondAdmin/lib/modules/angular-ui-tree/angular-ui-tree.min.css'
                        ]
                    },{
                    	name:'ui-tree-nestable',
                    	files: [
								'resource/others/BeyondAdmin/lib/modules/angular-ui-tree/angular-ui-tree-nestable.js'
                        ]
                    },{
                    	name:'kindeditor',
                    	serie:true,
                    	files: [
								'resource/others/BeyondAdmin/lib/jquery/kindeditor/kindeditor.js',
								'resource/others/BeyondAdmin/lib/jquery/kindeditor/angular-kindeditor.js'
                        ]
                    },{
                    	name:'cgBusy',
                    	serie:true,
                    	files: [
								'resource/others/BeyondAdmin/lib/modules/angular-busy/angular-busy.min.js',
								'resource/others/BeyondAdmin/lib/modules/angular-busy/angular-busy.min.css'
                        ]
                    }
                ]
            });
        }
    ]);