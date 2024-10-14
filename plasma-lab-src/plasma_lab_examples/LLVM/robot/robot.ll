; ModuleID = '/tmp/robot.ll'
source_filename = "llvm-link"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%struct.pthread_mutex_t = type <{ i8, i64, i64 }>
%struct.Comm = type { i32, i32, i32, i32, i32, i32 }
%struct.pthread_attr_t = type { i32 }

@xPos = global i32 0, align 4
@yPos = global i32 8, align 4
@mutex = common global %struct.pthread_mutex_t zeroinitializer, align 1
@grid = common global [81 x i32] zeroinitializer, align 16

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @done() #0 {
bb:
  ret void
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i8* @observer(i8* %arg) #0 {
bb:
  %tmp = alloca i8*, align 8
  %tmp1 = alloca %struct.Comm*, align 8
  store i8* %arg, i8** %tmp, align 8
  %tmp2 = load i8*, i8** %tmp, align 8
  %tmp3 = bitcast i8* %tmp2 to %struct.Comm*
  store %struct.Comm* %tmp3, %struct.Comm** %tmp1, align 8
  br label %bb4

bb4:                                              ; preds = %bb9, %bb
  %tmp5 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp6 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp5, i32 0, i32 5
  %tmp7 = load i32, i32* %tmp6, align 4
  %tmp8 = icmp ne i32 %tmp7, 0
  br i1 %tmp8, label %bb9, label %bb25

bb9:                                              ; preds = %bb4
  %tmp10 = call i32 (...) bitcast (i32 ()* @goal to i32 (...)*)() #3
  %tmp11 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp12 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp11, i32 0, i32 4
  store i32 %tmp10, i32* %tmp12, align 4
  %tmp13 = call i32 (...) bitcast (i32 ()* @lookUp to i32 (...)*)() #3
  %tmp14 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp15 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp14, i32 0, i32 0
  store i32 %tmp13, i32* %tmp15, align 4
  %tmp16 = call i32 (...) bitcast (i32 ()* @lookDown to i32 (...)*)() #3
  %tmp17 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp18 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp17, i32 0, i32 1
  store i32 %tmp16, i32* %tmp18, align 4
  %tmp19 = call i32 (...) bitcast (i32 ()* @lookRight to i32 (...)*)() #3
  %tmp20 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp21 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp20, i32 0, i32 3
  store i32 %tmp19, i32* %tmp21, align 4
  %tmp22 = call i32 (...) bitcast (i32 ()* @lookLeft to i32 (...)*)() #3
  %tmp23 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp24 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp23, i32 0, i32 2
  store i32 %tmp22, i32* %tmp24, align 4
  br label %bb4

bb25:                                             ; preds = %bb4
  ret i8* null
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i8* @runner(i8* %arg) #0 {
bb:
  %tmp = alloca i8*, align 8
  %tmp1 = alloca %struct.Comm*, align 8
  %tmp2 = alloca i64, align 8
  %tmp3 = alloca i32, align 4
  %tmp4 = alloca i32, align 4
  store i8* %arg, i8** %tmp, align 8
  %tmp5 = load i8*, i8** %tmp, align 8
  %tmp6 = bitcast i8* %tmp5 to %struct.Comm*
  store %struct.Comm* %tmp6, %struct.Comm** %tmp1, align 8
  %tmp7 = call i64 @time(i64* %tmp2) #4
  %tmp8 = trunc i64 %tmp7 to i32
  %tmp9 = call i32 (i32, ...) bitcast (i32 (...)* @srand to i32 (i32, ...)*)(i32 %tmp8) #3
  store i32 0, i32* %tmp3, align 4
  br label %bb10

bb10:                                             ; preds = %bb54, %bb
  %tmp11 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp12 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp11, i32 0, i32 4
  %tmp13 = load i32, i32* %tmp12, align 4
  %tmp14 = icmp ne i32 %tmp13, 0
  %tmp15 = xor i1 %tmp14, true
  br i1 %tmp15, label %bb16, label %bb55

bb16:                                             ; preds = %bb10
  %tmp17 = load i32, i32* %tmp3, align 4
  %tmp18 = add nsw i32 %tmp17, 1
  store i32 %tmp18, i32* %tmp3, align 4
  %tmp19 = call i32 @rand() #3
  %tmp20 = srem i32 %tmp19, 4
  store i32 %tmp20, i32* %tmp4, align 4
  %tmp21 = load i32, i32* %tmp4, align 4
  switch i32 %tmp21, label %bb54 [
    i32 0, label %bb22
    i32 1, label %bb30
    i32 2, label %bb38
    i32 3, label %bb46
  ]

bb22:                                             ; preds = %bb16
  %tmp23 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp24 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp23, i32 0, i32 0
  %tmp25 = load i32, i32* %tmp24, align 4
  %tmp26 = icmp ne i32 %tmp25, 0
  br i1 %tmp26, label %bb27, label %bb29

bb27:                                             ; preds = %bb22
  %tmp28 = call i32 (...) bitcast (i32 ()* @moveUp to i32 (...)*)() #3
  br label %bb29

bb29:                                             ; preds = %bb27, %bb22
  br label %bb54

bb30:                                             ; preds = %bb16
  %tmp31 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp32 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp31, i32 0, i32 1
  %tmp33 = load i32, i32* %tmp32, align 4
  %tmp34 = icmp ne i32 %tmp33, 0
  br i1 %tmp34, label %bb35, label %bb37

bb35:                                             ; preds = %bb30
  %tmp36 = call i32 (...) bitcast (i32 ()* @moveDown to i32 (...)*)() #3
  br label %bb37

bb37:                                             ; preds = %bb35, %bb30
  br label %bb54

bb38:                                             ; preds = %bb16
  %tmp39 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp40 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp39, i32 0, i32 2
  %tmp41 = load i32, i32* %tmp40, align 4
  %tmp42 = icmp ne i32 %tmp41, 0
  br i1 %tmp42, label %bb43, label %bb45

bb43:                                             ; preds = %bb38
  %tmp44 = call i32 (...) bitcast (i32 ()* @moveLeft to i32 (...)*)() #3
  br label %bb45

bb45:                                             ; preds = %bb43, %bb38
  br label %bb54

bb46:                                             ; preds = %bb16
  %tmp47 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp48 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp47, i32 0, i32 3
  %tmp49 = load i32, i32* %tmp48, align 4
  %tmp50 = icmp ne i32 %tmp49, 0
  br i1 %tmp50, label %bb51, label %bb53

bb51:                                             ; preds = %bb46
  %tmp52 = call i32 (...) bitcast (i32 ()* @moveRight to i32 (...)*)() #3
  br label %bb53

bb53:                                             ; preds = %bb51, %bb46
  br label %bb54

bb54:                                             ; preds = %bb53, %bb45, %bb37, %bb29, %bb16
  br label %bb10

bb55:                                             ; preds = %bb10
  %tmp56 = load %struct.Comm*, %struct.Comm** %tmp1, align 8
  %tmp57 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp56, i32 0, i32 5
  store i32 0, i32* %tmp57, align 4
  ret i8* null
}

; Function Attrs: nounwind
declare i64 @time(i64*) #1

declare i32 @srand(...) #2

declare i32 @rand() #2

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @gridReady() #0 {
bb:
  ret void
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @main() #0 {
bb:
  %tmp = alloca %struct.Comm, align 4
  %tmp1 = alloca i64, align 8
  %tmp2 = alloca i64, align 8
  call void (...) bitcast (void ()* @initGrid to void (...)*)() #3
  call void @gridReady() #3
  %tmp3 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp, i32 0, i32 5
  store i32 1, i32* %tmp3, align 4
  %tmp4 = getelementptr inbounds %struct.Comm, %struct.Comm* %tmp, i32 0, i32 4
  store i32 0, i32* %tmp4, align 4
  %tmp5 = bitcast %struct.Comm* %tmp to i8*
  %tmp6 = call i32 @pthread_create(i64* %tmp1, %struct.pthread_attr_t* null, i8* (i8*)* @observer, i8* %tmp5) #3
  %tmp7 = bitcast %struct.Comm* %tmp to i8*
  %tmp8 = call i32 @pthread_create(i64* %tmp2, %struct.pthread_attr_t* null, i8* (i8*)* @runner, i8* %tmp7) #3
  %tmp9 = load i64, i64* %tmp1, align 8
  %tmp10 = call i32 @pthread_join(i64 %tmp9, i8** null) #3
  %tmp11 = load i64, i64* %tmp2, align 8
  %tmp12 = call i32 @pthread_join(i64 %tmp11, i8** null) #3
  %tmp13 = call i32 (...) bitcast (i32 ()* @VERIFIERDone to i32 (...)*)() #3
  ret i32 0
}

declare i32 @pthread_create(i64*, %struct.pthread_attr_t*, i8* (i8*)*, i8*) #2

declare i32 @pthread_join(i64, i8**) #2

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @VERIFIERDone() #0 {
bb:
  %tmp = alloca i32, align 4
  br label %bb1

bb1:                                              ; preds = %bb1, %bb
  br label %bb1

bb2:                                              ; No predecessors!
  %tmp3 = load i32, i32* %tmp, align 4
  ret i32 %tmp3
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @VERIFIERCrash() #0 {
bb:
  %tmp = alloca i32, align 4
  br label %bb1

bb1:                                              ; preds = %bb1, %bb
  br label %bb1

bb2:                                              ; No predecessors!
  %tmp3 = load i32, i32* %tmp, align 4
  ret i32 %tmp3
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @lock() #0 {
bb:
  %tmp = call i32 @pthread_mutex_lock(%struct.pthread_mutex_t* @mutex) #3
  ret void
}

declare i32 @pthread_mutex_lock(%struct.pthread_mutex_t*) #2

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @unlock() #0 {
bb:
  %tmp = call i32 @pthread_mutex_unlock(%struct.pthread_mutex_t* @mutex) #3
  ret void
}

declare i32 @pthread_mutex_unlock(%struct.pthread_mutex_t*) #2

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @initGrid() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = alloca i32, align 4
  store i32 0, i32* %tmp, align 4
  br label %bb2

bb2:                                              ; preds = %bb20, %bb
  %tmp3 = load i32, i32* %tmp, align 4
  %tmp4 = icmp slt i32 %tmp3, 9
  br i1 %tmp4, label %bb5, label %bb23

bb5:                                              ; preds = %bb2
  store i32 0, i32* %tmp1, align 4
  br label %bb6

bb6:                                              ; preds = %bb16, %bb5
  %tmp7 = load i32, i32* %tmp1, align 4
  %tmp8 = icmp slt i32 %tmp7, 9
  br i1 %tmp8, label %bb9, label %bb19

bb9:                                              ; preds = %bb6
  %tmp10 = load i32, i32* %tmp1, align 4
  %tmp11 = mul nsw i32 9, %tmp10
  %tmp12 = load i32, i32* %tmp, align 4
  %tmp13 = add nsw i32 %tmp11, %tmp12
  %tmp14 = sext i32 %tmp13 to i64
  %tmp15 = getelementptr inbounds [81 x i32], [81 x i32]* @grid, i64 0, i64 %tmp14
  store i32 2, i32* %tmp15, align 4
  br label %bb16

bb16:                                             ; preds = %bb9
  %tmp17 = load i32, i32* %tmp1, align 4
  %tmp18 = add nsw i32 %tmp17, 1
  store i32 %tmp18, i32* %tmp1, align 4
  br label %bb6

bb19:                                             ; preds = %bb6
  br label %bb20

bb20:                                             ; preds = %bb19
  %tmp21 = load i32, i32* %tmp, align 4
  %tmp22 = add nsw i32 %tmp21, 1
  store i32 %tmp22, i32* %tmp, align 4
  br label %bb2

bb23:                                             ; preds = %bb2
  store i32 1, i32* getelementptr inbounds ([81 x i32], [81 x i32]* @grid, i64 0, i64 8), align 16
  %tmp24 = call i32 @pthread_mutex_init(%struct.pthread_mutex_t* @mutex, %struct.pthread_attr_t* null) #3
  ret void
}

declare i32 @pthread_mutex_init(%struct.pthread_mutex_t*, %struct.pthread_attr_t*) #2

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @innerLookUp() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = load i32, i32* @yPos, align 4
  %tmp2 = sub nsw i32 %tmp1, 1
  %tmp3 = icmp sge i32 %tmp2, 0
  br i1 %tmp3, label %bb4, label %bb13

bb4:                                              ; preds = %bb
  %tmp5 = load i32, i32* @yPos, align 4
  %tmp6 = sub nsw i32 %tmp5, 1
  %tmp7 = mul nsw i32 9, %tmp6
  %tmp8 = load i32, i32* @xPos, align 4
  %tmp9 = add nsw i32 %tmp7, %tmp8
  %tmp10 = sext i32 %tmp9 to i64
  %tmp11 = getelementptr inbounds [81 x i32], [81 x i32]* @grid, i64 0, i64 %tmp10
  %tmp12 = load i32, i32* %tmp11, align 4
  store i32 %tmp12, i32* %tmp, align 4
  br label %bb14

bb13:                                             ; preds = %bb
  store i32 0, i32* %tmp, align 4
  br label %bb14

bb14:                                             ; preds = %bb13, %bb4
  %tmp15 = load i32, i32* %tmp, align 4
  ret i32 %tmp15
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @innerLookDown() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = load i32, i32* @yPos, align 4
  %tmp2 = add nsw i32 %tmp1, 1
  %tmp3 = icmp slt i32 %tmp2, 9
  br i1 %tmp3, label %bb4, label %bb13

bb4:                                              ; preds = %bb
  %tmp5 = load i32, i32* @yPos, align 4
  %tmp6 = add nsw i32 %tmp5, 1
  %tmp7 = mul nsw i32 9, %tmp6
  %tmp8 = load i32, i32* @xPos, align 4
  %tmp9 = add nsw i32 %tmp7, %tmp8
  %tmp10 = sext i32 %tmp9 to i64
  %tmp11 = getelementptr inbounds [81 x i32], [81 x i32]* @grid, i64 0, i64 %tmp10
  %tmp12 = load i32, i32* %tmp11, align 4
  store i32 %tmp12, i32* %tmp, align 4
  br label %bb14

bb13:                                             ; preds = %bb
  store i32 0, i32* %tmp, align 4
  br label %bb14

bb14:                                             ; preds = %bb13, %bb4
  %tmp15 = load i32, i32* %tmp, align 4
  ret i32 %tmp15
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @innerLookRight() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = load i32, i32* @xPos, align 4
  %tmp2 = add nsw i32 %tmp1, 1
  %tmp3 = icmp slt i32 %tmp2, 9
  br i1 %tmp3, label %bb4, label %bb13

bb4:                                              ; preds = %bb
  %tmp5 = load i32, i32* @yPos, align 4
  %tmp6 = mul nsw i32 9, %tmp5
  %tmp7 = load i32, i32* @xPos, align 4
  %tmp8 = add nsw i32 %tmp6, %tmp7
  %tmp9 = add nsw i32 %tmp8, 1
  %tmp10 = sext i32 %tmp9 to i64
  %tmp11 = getelementptr inbounds [81 x i32], [81 x i32]* @grid, i64 0, i64 %tmp10
  %tmp12 = load i32, i32* %tmp11, align 4
  store i32 %tmp12, i32* %tmp, align 4
  br label %bb14

bb13:                                             ; preds = %bb
  store i32 0, i32* %tmp, align 4
  br label %bb14

bb14:                                             ; preds = %bb13, %bb4
  %tmp15 = load i32, i32* %tmp, align 4
  ret i32 %tmp15
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @innerLookLeft() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = load i32, i32* @xPos, align 4
  %tmp2 = sub nsw i32 %tmp1, 1
  %tmp3 = icmp sge i32 %tmp2, 0
  br i1 %tmp3, label %bb4, label %bb13

bb4:                                              ; preds = %bb
  %tmp5 = load i32, i32* @yPos, align 4
  %tmp6 = mul nsw i32 9, %tmp5
  %tmp7 = load i32, i32* @xPos, align 4
  %tmp8 = add nsw i32 %tmp6, %tmp7
  %tmp9 = sub nsw i32 %tmp8, 1
  %tmp10 = sext i32 %tmp9 to i64
  %tmp11 = getelementptr inbounds [81 x i32], [81 x i32]* @grid, i64 0, i64 %tmp10
  %tmp12 = load i32, i32* %tmp11, align 4
  store i32 %tmp12, i32* %tmp, align 4
  br label %bb14

bb13:                                             ; preds = %bb
  store i32 0, i32* %tmp, align 4
  br label %bb14

bb14:                                             ; preds = %bb13, %bb4
  %tmp15 = load i32, i32* %tmp, align 4
  ret i32 %tmp15
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @moveUp() #0 {
bb:
  %tmp = alloca i32, align 4
  call void @lock() #3
  %tmp1 = call i32 @innerLookUp() #3
  %tmp2 = icmp ne i32 %tmp1, 0
  br i1 %tmp2, label %bb3, label %bb6

bb3:                                              ; preds = %bb
  %tmp4 = load i32, i32* @yPos, align 4
  %tmp5 = add nsw i32 %tmp4, -1
  store i32 %tmp5, i32* @yPos, align 4
  br label %bb8

bb6:                                              ; preds = %bb
  %tmp7 = call i32 @VERIFIERCrash() #3
  br label %bb8

bb8:                                              ; preds = %bb6, %bb3
  call void @unlock() #3
  %tmp9 = load i32, i32* %tmp, align 4
  ret i32 %tmp9
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @moveDown() #0 {
bb:
  %tmp = alloca i32, align 4
  call void @lock() #3
  %tmp1 = call i32 @innerLookDown() #3
  %tmp2 = icmp ne i32 %tmp1, 0
  br i1 %tmp2, label %bb3, label %bb6

bb3:                                              ; preds = %bb
  %tmp4 = load i32, i32* @yPos, align 4
  %tmp5 = add nsw i32 %tmp4, 1
  store i32 %tmp5, i32* @yPos, align 4
  br label %bb8

bb6:                                              ; preds = %bb
  %tmp7 = call i32 @VERIFIERCrash() #3
  br label %bb8

bb8:                                              ; preds = %bb6, %bb3
  call void @unlock() #3
  %tmp9 = load i32, i32* %tmp, align 4
  ret i32 %tmp9
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @moveLeft() #0 {
bb:
  %tmp = alloca i32, align 4
  call void @lock() #3
  %tmp1 = call i32 @innerLookLeft() #3
  %tmp2 = icmp ne i32 %tmp1, 0
  br i1 %tmp2, label %bb3, label %bb6

bb3:                                              ; preds = %bb
  %tmp4 = load i32, i32* @xPos, align 4
  %tmp5 = add nsw i32 %tmp4, -1
  store i32 %tmp5, i32* @xPos, align 4
  br label %bb8

bb6:                                              ; preds = %bb
  %tmp7 = call i32 @VERIFIERCrash() #3
  br label %bb8

bb8:                                              ; preds = %bb6, %bb3
  call void @unlock() #3
  %tmp9 = load i32, i32* %tmp, align 4
  ret i32 %tmp9
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @moveRight() #0 {
bb:
  %tmp = alloca i32, align 4
  call void @lock() #3
  %tmp1 = call i32 @innerLookRight() #3
  %tmp2 = icmp ne i32 %tmp1, 0
  br i1 %tmp2, label %bb3, label %bb6

bb3:                                              ; preds = %bb
  %tmp4 = load i32, i32* @xPos, align 4
  %tmp5 = add nsw i32 %tmp4, 1
  store i32 %tmp5, i32* @xPos, align 4
  br label %bb8

bb6:                                              ; preds = %bb
  %tmp7 = call i32 @VERIFIERCrash() #3
  br label %bb8

bb8:                                              ; preds = %bb6, %bb3
  call void @unlock() #3
  %tmp9 = load i32, i32* %tmp, align 4
  ret i32 %tmp9
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @lookUp() #0 {
bb:
  %tmp = alloca i32, align 4
  call void @lock() #3
  %tmp1 = call i32 @innerLookUp() #3
  store i32 %tmp1, i32* %tmp, align 4
  call void @unlock() #3
  %tmp2 = load i32, i32* %tmp, align 4
  ret i32 %tmp2
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @lookDown() #0 {
bb:
  %tmp = alloca i32, align 4
  call void @lock() #3
  %tmp1 = call i32 @innerLookDown() #3
  store i32 %tmp1, i32* %tmp, align 4
  call void @unlock() #3
  %tmp2 = load i32, i32* %tmp, align 4
  ret i32 %tmp2
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @lookLeft() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = alloca i32, align 4
  call void @lock() #3
  %tmp2 = call i32 @innerLookLeft() #3
  store i32 %tmp2, i32* %tmp1, align 4
  call void @unlock() #3
  %tmp3 = load i32, i32* %tmp, align 4
  ret i32 %tmp3
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @lookRight() #0 {
bb:
  %tmp = alloca i32, align 4
  call void @lock() #3
  %tmp1 = call i32 @innerLookRight() #3
  store i32 %tmp1, i32* %tmp, align 4
  call void @unlock() #3
  %tmp2 = load i32, i32* %tmp, align 4
  ret i32 %tmp2
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @goal() #0 {
bb:
  %tmp = load i32, i32* @yPos, align 4
  %tmp1 = mul nsw i32 %tmp, 9
  %tmp2 = load i32, i32* @xPos, align 4
  %tmp3 = add nsw i32 %tmp1, %tmp2
  %tmp4 = sext i32 %tmp3 to i64
  %tmp5 = getelementptr inbounds [81 x i32], [81 x i32]* @grid, i64 0, i64 %tmp4
  %tmp6 = load i32, i32* %tmp5, align 4
  %tmp7 = icmp eq i32 %tmp6, 1
  %tmp8 = zext i1 %tmp7 to i32
  ret i32 %tmp8
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @getX() #0 {
bb:
  %tmp = load i32, i32* @xPos, align 4
  ret i32 %tmp
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @getY() #0 {
bb:
  %tmp = load i32, i32* @yPos, align 4
  ret i32 %tmp
}

attributes #0 = { noinline nounwind optnone sspstrong uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nounwind "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { nobuiltin }
attributes #4 = { nobuiltin nounwind }

!llvm.ident = !{!0, !0}
!llvm.module.flags = !{!1, !2, !3}

!0 = !{!"clang version 5.0.0 (tags/RELEASE_500/final)"}
!1 = !{i32 1, !"wchar_size", i32 4}
!2 = !{i32 7, !"PIC Level", i32 2}
!3 = !{i32 7, !"PIE Level", i32 2}
