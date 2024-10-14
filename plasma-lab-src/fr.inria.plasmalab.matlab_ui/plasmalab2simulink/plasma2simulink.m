function varargout = plasma2simulink(varargin)
% PLASMA2SIMULINK MATLAB code for plasma2simulink.fig
%      PLASMA2SIMULINK, by itself, creates a new PLASMA2SIMULINK or raises the existing
%      singleton*.
%
%      H = PLASMA2SIMULINK returns the handle to a new PLASMA2SIMULINK or the handle to
%      the existing singleton*.
%
%      PLASMA2SIMULINK('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in PLASMA2SIMULINK.M with the given input arguments.
%
%      PLASMA2SIMULINK('Property','Value',...) creates a new PLASMA2SIMULINK or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before plasma2simulink_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to plasma2simulink_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @plasma2simulink_OpeningFcn, ...
                   'gui_OutputFcn',  @plasma2simulink_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before plasma2simulink is made visible.
function plasma2simulink_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to plasma2simulink (see VARARGIN)

% Choose default command line output for plasma2simulink
handles.output = hObject;

% Handle display fcn
p2s_displayMsg_handle=@p2s_displayMsg;
setappdata(0,'p2s_displayMsg',p2s_displayMsg_handle);
p2s_start_handle=@p2s_start;
setappdata(0,'p2s_start',p2s_start_handle);
p2s_completed_handle=@p2s_completed;
setappdata(0,'p2s_completed',p2s_completed_handle);
p2s_error_handle=@p2s_error;
setappdata(0,'p2s_error',p2s_error_handle);

% where is PLASMA Lab
plasma_path = which('${project.build.finalName}.jar');
javaaddpath(plasma_path);
matlab_ui = fr.inria.plasmalab.matlab_ui.Matlab_UI();

version = char(javaMethod('getPlasmaVersionNumber',matlab_ui));
set(handles.plasmaVersion,'String',  strcat('PLASMA Lab v',version));

set(handles.version,'String','PLASMA2Simulink v${project.version}');

axes(handles.axes1);
imshow('plasma2simulink_header.png');
% axes(handles.axes3);
% imshow('img/circle1.png');
% axes(handles.axes4);
% imshow('img/circle0.png');
% axes(handles.axes5);
% imshow('img/circle0.png');
% UIWAIT makes plasma2simulink wait for user response (see UIRESUME)
% uiwait(handles.figure1);

% Update handles structure
guidata(hObject, handles);

% --- Outputs from this function are returned to the command line.
function varargout = plasma2simulink_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;



function edit1_Callback(hObject, eventdata, handles)
% hObject    handle to edit1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of edit1 as text
%        str2double(get(hObject,'String')) returns contents of edit1 as a double


% --- Executes during object creation, after setting all properties.
function edit1_CreateFcn(hObject, eventdata, handles)
% hObject    handle to edit1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in go_button.
function go_button_Callback(hObject, eventdata, handles)
% hObject    handle to go_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
set(handles.terminal,'String','Initialization');
% Update handles structure
guidata(hObject, handles);

model_str = get(handles.model, 'String');
req_str = get(handles.requirement, 'String'); 
val = get(handles.algo_name,'Value');
switch val
case 1
    algo_name_str = 'montecarlo';
case 2
    algo_name_str = 'chernoff';
case 3
    algo_name_str = 'sequential';
case 4
    algo_name_str = 'sequentialmdp';
case 5
    algo_name_str = 'iteratedmdp';
case 6
    algo_name_str = 'splitting';
end;
algo_param_str = get(handles.algo_parameters, 'String');

matlab_ui = fr.inria.plasmalab.matlab_ui.Matlab_UI();
javaMethod('InitExperiment',matlab_ui, model_str,req_str,algo_name_str,algo_param_str);



% --- If Enable == 'on', executes on mouse press in 5 pixel border.
% --- Otherwise, executes on mouse press in 5 pixel border or over go_button.
function go_button_ButtonDownFcn(hObject, eventdata, handles)
% hObject    handle to go_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)



function edit2_Callback(hObject, eventdata, handles)
% hObject    handle to edit2 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of edit2 as text
%        str2double(get(hObject,'String')) returns contents of edit2 as a double


% --- Executes during object creation, after setting all properties.
function edit2_CreateFcn(hObject, eventdata, handles)
% hObject    handle to edit2 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in pushbutton2.
function pushbutton2_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton2 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on selection change in listbox2.
function listbox2_Callback(hObject, eventdata, handles)
% hObject    handle to listbox2 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = cellstr(get(hObject,'String')) returns listbox2 contents as cell array
%        contents{get(hObject,'Value')} returns selected item from listbox2


% --- Executes during object creation, after setting all properties.
function listbox2_CreateFcn(hObject, eventdata, handles)
% hObject    handle to listbox2 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: listbox controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function model_Callback(hObject, eventdata, handles)
% hObject    handle to algo_name (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of algo_name as text
%        str2double(get(hObject,'String')) returns contents of algo_name as a double

% --- Executes during object creation, after setting all properties.
function model_CreateFcn(hObject, eventdata, handles)
% hObject    handle to algo_name (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in browse_button.
function browse_button_Callback(hObject, eventdata, handles)
% hObject    handle to browse_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
[FileName,PathName,FilterIndex] = uigetfile({'*.mdl;*.slx','Models (*.slx, *.mdl)';...
    '*.*',  'All Files (*.*)'},'Select Simulink model');
if FileName ~= 0
    set(handles.model,'String',strcat(PathName,FileName));
end

function algo_parameters_Callback(hObject, eventdata, handles)
% hObject    handle to algo_parameters (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of algo_parameters as text
%        str2double(get(hObject,'String')) returns contents of algo_parameters as a double


% --- Executes during object creation, after setting all properties.
function algo_parameters_CreateFcn(hObject, eventdata, handles)
% hObject    handle to algo_parameters (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on slider movement.
function slider3_Callback(hObject, eventdata, handles)
% hObject    handle to slider3 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function slider3_CreateFcn(hObject, eventdata, handles)
% hObject    handle to slider3 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end


% --- Executes on slider movement.
function slider4_Callback(hObject, eventdata, handles)
% hObject    handle to slider4 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function slider4_CreateFcn(hObject, eventdata, handles)
% hObject    handle to slider4 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end



function terminal_Callback(hObject, eventdata, handles)
% hObject    handle to terminal (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of terminal as text
%        str2double(get(hObject,'String')) returns contents of terminal as a double


% --- Executes during object creation, after setting all properties.
function terminal_CreateFcn(hObject, eventdata, handles)
% hObject    handle to terminal (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


function p2s_displayMsg(msg)
    handles = guihandles(plasma2simulink);
    old = [get(handles.terminal, 'String');{msg}];
    set(handles.terminal,'String',old);
    
function p2s_start()
    handles = guihandles(plasma2simulink);
    old = [get(handles.terminal, 'String');{'Algorithm started'}];
    set(handles.terminal,'String',old);
    
function p2s_completed()
        
function p2s_error(errorMsg)
    handles = guihandles(plasma2simulink);
    old = [get(handles.terminal, 'String');{errorMsg};...
        {'Algorithm stopped with an error.'}];
    set(handles.terminal,'String',old);

% --- Executes on selection change in algo_name.
function algo_name_Callback(hObject, eventdata, handles)
% hObject    handle to algo_name (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = cellstr(get(hObject,'String')) returns algo_name contents as cell array
%        contents{get(hObject,'Value')} returns selected item from algo_name


% --- Executes during object creation, after setting all properties.
function algo_name_CreateFcn(hObject, eventdata, handles)
% hObject    handle to algo_name (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
data = ['Monte Carlo';'Chernoff  ';'Sequential'];
celldata = cellstr(data);
set(hObject,'String',celldata);
