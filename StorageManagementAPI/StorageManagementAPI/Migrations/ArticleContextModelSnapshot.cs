﻿// <auto-generated />
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using StorageManagementAPI.Models;

namespace StorageManagementAPI.Migrations
{
    [DbContext(typeof(ArticleContext))]
    partial class ArticleContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062");

            modelBuilder.Entity("StorageManagementAPI.Models.Article", b =>
                {
                    b.Property<string>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("label");

                    b.Property<int>("minStock");

                    b.Property<float>("price");

                    b.HasKey("id");

                    b.ToTable("articles");
                });
#pragma warning restore 612, 618
        }
    }
}
