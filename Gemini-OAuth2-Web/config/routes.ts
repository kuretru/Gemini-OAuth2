export default [
  {
    path: '/welcome',
    component: './Welcome',
    name: '欢迎',
    icon: 'smile',
  },
  {
    path: '/users',
    routes: [
      {
        path: '/users',
        routes: [
          {
            path: '/users',
            component: './user/AccountSettings',
            name: '个人设置',
            icon: 'smile',
          },
          {
            path: '/users/login',
            component: './user/Login',
            name: '登录',
            layout: false,
          },
        ],
      },
    ],
  },
  {
    path: '/oauth/applications',
    component: './oauth/Application',
    name: 'OAuth2应用管理',
    icon: 'appstore',
  },

  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    component: './Admin',
    routes: [
      {
        path: '/admin/sub-page',
        name: '二级管理页',
        icon: 'smile',
        component: './Welcome',
      },
      {
        component: './404',
      },
    ],
  },
  {
    name: '查询表格',
    icon: 'table',
    path: '/list',
    component: './TableList',
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './404',
  },
];
